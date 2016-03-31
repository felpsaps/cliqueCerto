<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${dados}" var="d">
${d.preco}|${d.tempo}|${d.status}|${d.lances}|${d.ultimoLance},
</c:forEach>