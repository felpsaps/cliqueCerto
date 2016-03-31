package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import model.AtualizarDadosBean;
import model.LeilaoBean;
import model.UsuarioBean;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import DAO.LeilaoDAO;

@Controller
public class LeilaoController {

	private static final Map<String, LeilaoBean> leiloesMap = new HashMap<String, LeilaoBean>();
	
	static {
		try{
			List<LeilaoBean> leilao = new LeilaoDAO().getLeiloesHome();
			for (LeilaoBean l : leilao) {
				leiloesMap.put("leilao_" + l.getId(), l);
			}
			
			// Thread para verificar horario dos leiloes
			iniciaLeilaoThread();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void iniciaLeilaoThread() {
		Thread iniciaLeilaoThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						final Timestamp dataAtual = new Timestamp(new Date().getTime());
						for (LeilaoBean l : leiloesMap.values()) {
							if (l.getDtInicio().before(dataAtual) && "F".equals(l.getStatus())) {
								iniciarLeilao(l);
							}
						}
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
				
			}
		});
		iniciaLeilaoThread.start();		
	}
	
	/* Este metodo sera executado quando um leilao iniciar,
	 * ou seja, ele sera executado automaticamente pelo servidor */
	public static void iniciarLeilao(final LeilaoBean leilao) {
		try {
			new LeilaoDAO().iniciarLeilao(leilao.getId());
			leiloesMap.get("leilao_" + leilao.getId()).setStatus("A");
			
			Thread leilaoThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					final LeilaoBean leilaoIniciado = leilao; 
					while(leiloesMap.get("leilao_" + leilaoIniciado.getId()).getTempoAtual() > 0) {
						try {
							Integer tempo = leiloesMap.get("leilao_" + leilaoIniciado.getId()).getTempoAtual();
							leiloesMap.get("leilao_" + leilaoIniciado.getId()).setTempoAtual(--tempo);
							if (tempo == 0) {
								break; // Para nao esperar mais um segundo
							}
							Thread.sleep(1200);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
					try {
						new LeilaoDAO().encerrarLeilao(leilaoIniciado.getId());
						leiloesMap.get("leilao_" + leilao.getId()).setStatus("E");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
			leilaoThread.start();	
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	@RequestMapping (value="/jsp/montarHome.do", method = RequestMethod.POST)
	public String montarHome(Model model) {
		
		try{
			List<LeilaoBean> leiloes = new LeilaoDAO().getLeiloesHome();			
			model.addAttribute("leiloes", leiloes);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return "/home";
	}
	
	@RequestMapping (value="/jsp/montarHome.do", method = RequestMethod.GET)
	public String montarHomeGet(Model model) {
		
		try{
			List<LeilaoBean> leiloes = new LeilaoDAO().getLeiloesHome();			
			model.addAttribute("leiloes", leiloes);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return "/home";
	}
	
	@RequestMapping (value="/jsp/atualizaRelogio.do", method = RequestMethod.POST)
	public String getRelogio(Model model) {

		Date relogio = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMM 'de' yyyy - HH:mm", new Locale ("pt", "BR"));
		model.addAttribute("relogio", format.format(relogio));

		return "/atualizador/atualizaRelogio";
	}	
	
	
	@RequestMapping (value="/jsp/lanceFromHome.do", method = RequestMethod.POST)
	public String lance(@RequestParam(required=false,value="leilaoId") Long leilaoId,
						Model model, HttpSession session) {

		UsuarioBean usr = (UsuarioBean)session.getAttribute("usr");
		if (usr == null ) {
			// redireciona para tela de login
			return atualizaDadosHome(String.valueOf(leilaoId), model, session);
		}
		if (usr.getLances() <= 0) {
			//redireciona para tela de comprar
			return atualizaDadosHome(String.valueOf(leilaoId), model, session);
		}
		try {	
			leiloesMap.get("leilao_" + leilaoId).setPreco(leiloesMap.get("leilao_" + leilaoId).getPreco().add(new BigDecimal(0.01)).setScale(2, RoundingMode.HALF_EVEN));
			leiloesMap.get("leilao_" + leilaoId).setTempoAtual(leiloesMap.get("leilao_" + leilaoId).getTempo());
			leiloesMap.get("leilao_" + leilaoId).setUsrArremate(usr.getLogin());
	
//			model.addAttribute("preco", leiloesMap.get("leilao_" + leilaoId).getPreco());
//			model.addAttribute("tempo", leiloesMap.get("leilao_" + leilaoId).getTempoAtual());
//			model.addAttribute("tempo", leiloesMap.get("leilao_" + leilaoId).getTempoAtual());
			
			new LeilaoDAO().computarLance(leilaoId, leiloesMap.get("leilao_" + leilaoId).getPreco(), usr);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return atualizaDadosHome(String.valueOf(leilaoId), model, session);
		
	}
	
	@RequestMapping (value="/jsp/entrar.do", method = RequestMethod.POST)
	public String entrar(@RequestParam(required=false,value="leilaoId") Long leilaoId,
						Model model, HttpSession session) {
		
		//TODO pegao informaçoes leilao
		
//		model.addAttribute("preco", getPreco(leilaoId));
		model.addAttribute("preco", leiloesMap.get("leilao_" + leilaoId).getPreco());
		model.addAttribute("tempo", leiloesMap.get("leilao_" + leilaoId).getTempoAtual());
		//model.addAttribute("tempo", tempoLeilaoMap.get("leilao_" + leilaoId));
		return "/leilaoDetalhes";
	}
	
	@RequestMapping (value="/jsp/atualizaDados.do", method = RequestMethod.POST)
	public String atualizaDados(@RequestParam(required=true,value="leilaoId") Long leilaoId,
								Model model) {
		
		//model.addAttribute("preco", getPreco(leilaoId));
		model.addAttribute("preco", leiloesMap.get("leilao_" + leilaoId).getPreco());
		model.addAttribute("tempo", leiloesMap.get("leilao_" + leilaoId).getTempoAtual());
		//model.addAttribute("tempo", tempoLeilaoMap.get("leilao_" + leilaoId));
		return "/atualizaDadosLeilao";
	}
	
	@RequestMapping (value="/jsp/atualizaDadosHome.do", method = RequestMethod.POST)
	public String atualizaDadosHome(@RequestParam(required=true,value="leilaoId") String id,
									Model model, HttpSession session) {
		if (!leiloesMap.isEmpty()) {
			int i = 1;
			String[] ids = id.split(",");
			List<AtualizarDadosBean> listaDados = new ArrayList<AtualizarDadosBean>();
			for(String a : ids) {
				if (!"".equals(a)) {
					AtualizarDadosBean dados = new AtualizarDadosBean();
					UsuarioBean usr = (UsuarioBean)session.getAttribute("usr");
					if (usr != null ) {
						dados.setLances(usr.getLances());
					}
					dados.setPreco(leiloesMap.get("leilao_" + a).getPreco());
					dados.setTempo(leiloesMap.get("leilao_" + a).getTempoAtual());
					dados.setStatus(leiloesMap.get("leilao_" + a).getStatus());
					dados.setUltimoLance(leiloesMap.get("leilao_" + a).getUsrArremate());
					
					listaDados.add(dados);
//					model.addAttribute("preco_" + i, leiloesMap.get("leilao_" + a).getPreco());
//					model.addAttribute("tempo_" + i, leiloesMap.get("leilao_" + a).getTempoAtual());
//					model.addAttribute("status_" + i,leiloesMap.get("leilao_" + a).getStatus());
//					i++;
				}
			}
			model.addAttribute("dados", listaDados);
		}
		return "/atualizaDadosLeilaoHome";
	}
}
