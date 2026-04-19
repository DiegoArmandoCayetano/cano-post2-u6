<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Formulario Producto</h2>

<!-- ================= ERRORES ================= -->
<c:if test="${not empty errores}">
    <ul style="color:red">
        <c:forEach var="e" items="${errores}">
            <li>${e.value}</li>
        </c:forEach>
    </ul>
</c:if>

<!-- ================= FORM ================= -->
<form method="post" action="${pageContext.request.contextPath}/productos">

    <input type="hidden" name="id" value="${producto.id}">

    <!-- NOMBRE -->
    <label>Nombre:</label><br>
    <input type="text" name="nombre" value="${nombre != null ? nombre : producto.nombre}">
    <span style="color:red">${errores.nombre}</span>
    <br><br>

    <!-- CATEGORIA -->
    <label>Categoría:</label><br>
    <input type="text" name="categoria" value="${categoria != null ? categoria : producto.categoria}">
    <br><br>

    <!-- PRECIO -->
    <label>Precio:</label><br>
    <input type="text" name="precio" value="${precio != null ? precio : producto.precio}">
    <span style="color:red">${errores.precio}</span>
    <br><br>

    <!-- STOCK -->
    <label>Stock:</label><br>
    <input type="text" name="stock" value="${stock != null ? stock : producto.stock}">
    <span style="color:red">${errores.stock}</span>
    <br><br>

    <!-- ACCIÓN -->
    <c:choose>
        <c:when test="${not empty producto}">
            <input type="hidden" name="accion" value="actualizar">
            <button type="submit">Actualizar</button>
        </c:when>
        <c:otherwise>
            <input type="hidden" name="accion" value="guardar">
            <button type="submit">Guardar</button>
        </c:otherwise>
    </c:choose>

</form>

<br>
<a href="${pageContext.request.contextPath}/productos?accion=listar">
    Volver
</a>