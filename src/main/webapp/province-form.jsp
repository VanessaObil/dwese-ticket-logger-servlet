<%@ include file="header.jsp" %>

    <h1>
        <c:choose>
            <c:when test="${province == null}">
                <fmt:message key="msg.province-form.add" />
            </c:when>
            <c:otherwise>
                <fmt:message key="msg.province-form.edit" />
            </c:otherwise>
        </c:choose>
    </h1>

    <form action="provinces" method="post">
        <!-- Campo oculto para el ID de la provincia -->
        <input type="hidden" name="id" value="${province != null ? province.id : ''}" />
        <input type="hidden" name="action" value="${province == null ? 'insert' : 'update'}" />

        <!-- Campo para el código de la provincia -->
        <label for="code"><fmt:message key='msg.province-form.code' />:</label>
        <input type="text" name="code" id="code" value="${province != null ? province.code : ''}" required />

        <!-- Campo para el nombre de la provincia -->
        <label for="name"><fmt:message key='msg.province-form.name' />:</label>
        <input type="text" name="name" id="name" value="${province != null ? province.name : ''}" required />

        <!-- Campo para seleccionar la región -->
        <label for="region"><fmt:message key='msg.province-form.region.id' />:</label>
        <select name="id_region" id="region" required>
            <option value="">Seleccione una comunidad autónoma</option>
            <!-- Iterar sobre la lista de regiones -->
            <c:forEach var="region" items="${listRegions}">
                <option value="${region.id}"
                    <c:if test="${province != null && province.region != null && province.region.id == region.id}">
                        selected
                    </c:if>
                >${region.name}</option>
            </c:forEach>
        </select>

        <!-- Botones de acción -->
        <c:choose>
            <c:when test="${province == null}">
                <input type="submit" value="<fmt:message key='msg.province-form.create' />" />
            </c:when>
            <c:otherwise>
                <input type="submit" value="<fmt:message key='msg.province-form.update' />" />
            </c:otherwise>
        </c:choose>
    </form>

    <a href="provinces"><fmt:message key="msg.province-form.returnback" /></a>

<%@ include file="footer.jsp" %>
