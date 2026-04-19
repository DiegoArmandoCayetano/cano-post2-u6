<c:if test="${not empty errores}">
 <div class="alert-error">
 <ul><c:forEach var="e"
items="${errores}"><li>${e.value}</li></c:forEach></ul>
 </div>
</c:if>
<label>Nombre:
 <input type="text" name="nombre" value="<c:out value="${nombre}"/>"
 class="${not empty errores.nombre ? 'input-error' : ''}">
 <c:if test="${not empty errores.nombre}">
 <span class="campo-error">${errores.nombre}</span>
 </c:if>
</label>