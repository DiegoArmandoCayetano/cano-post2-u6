<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'es'}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="app.titulo"/></title>
</head>

<body>

<!-- ================= HEADER ================= -->
<h1><fmt:message key="app.titulo"/></h1>

<p>
    <fmt:message key="app.bienvenida"/>,
    <b>${sessionScope.usuarioActual.username}</b>
</p>

<hr>

<!-- ================= MENSAJE POST-REDIRECT-GET ================= -->
<c:if test="${not empty param.mensaje}">
    <div style="color:green; font-weight:bold;">
        ${param.mensaje}
    </div>
</c:if>

<!-- ================= SELECTOR IDIOMA ================= -->
<p>
    🌍 Idioma:
    <a href="${pageContext.request.contextPath}/idioma?lang=es">Español</a> |
    <a href="${pageContext.request.contextPath}/idioma?lang=en">English</a>
</p>

<!-- ================= BOTÓN NUEVO ================= -->
<p>
    <a href="${pageContext.request.contextPath}/productos?accion=formulario">
        ➕ Nuevo Producto
    </a>
</p>

<!-- ================= TABLA PRODUCTOS ================= -->
<table border="1" cellpadding="8" cellspacing="0">

    <thead>
    <tr>
        <th>ID</th>
        <th><fmt:message key="tabla.nombre"/></th>
        <th><fmt:message key="tabla.categoria"/></th>
        <th><fmt:message key="tabla.precio"/></th>
        <th><fmt:message key="tabla.stock"/></th>
        <th><fmt:message key="tabla.acciones"/></th>
    </tr>
    </thead>

    <tbody>

    <c:forEach var="p" items="${productos}">
        <tr>
            <td>${p.id}</td>
            <td>${p.nombre}</td>
            <td>${p.categoria}</td>
            <td>${p.precio}</td>
            <td>${p.stock}</td>

            <td>

                <!-- EDITAR -->
                <a href="${pageContext.request.contextPath}/productos?accion=editar&id=${p.id}">
                    ✏ Editar
                </a>

                |

                <!-- ELIMINAR -->
                <a href="${pageContext.request.contextPath}/productos?accion=eliminar&id=${p.id}"
                   onclick="return confirm('¿Seguro que deseas eliminar este producto?');">
                    🗑 Eliminar
                </a>

            </td>
        </tr>
    </c:forEach>

    </tbody>

</table>

<hr>

<!-- ================= LOGOUT ================= -->
<p>
    <a href="${pageContext.request.contextPath}/logout">
        🚪 Cerrar sesión
    </a>
</p>

</body>
</html>