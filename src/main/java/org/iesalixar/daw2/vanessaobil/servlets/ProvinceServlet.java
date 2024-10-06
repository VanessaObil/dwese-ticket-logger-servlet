package org.iesalixar.daw2.vanessaobil.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesalixar.daw2.vanessaobil.dao.ProvinceDAO;
import org.iesalixar.daw2.vanessaobil.dao.ProvinceDAOImpl;
import org.iesalixar.daw2.vanessaobil.dao.RegionDAO;
import org.iesalixar.daw2.vanessaobil.dao.RegionDAOImpl;
import org.iesalixar.daw2.vanessaobil.entity.Province;
import org.iesalixar.daw2.vanessaobil.entity.Region;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


/**
 * Servlet que maneja las operaciones CRUD para la entidad `Province`.
 * Utiliza `ProvinceDAO` para interactuar con la base de datos.
 */
@WebServlet("/provinces")
public class ProvinceServlet extends HttpServlet {


    // DAO para gestionar las operaciones de las provincias en la base de datos
    private RegionDAO regionDAO;
    private ProvinceDAO provinceDAO;

    @Override
    public void init() throws ServletException {
        try {
            regionDAO = new RegionDAOImpl();
            provinceDAO = new ProvinceDAOImpl();
        } catch (Exception e) {
            throw new ServletException("Error al inicializar el ProvinceDAO", e);
        }
    }


    /**
     * Maneja las solicitudes GET al servlet. Según el parámetro "action", decide qué método invocar.
     * @param request  Solicitud HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException en caso de errores en el servlet.
     * @throws IOException en caso de errores de E/S.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");


        try {
            // Manejo para evitar el valor null
            if (action == null) {
                action = "list"; // O cualquier acción predeterminada que desees manejar
            }


            switch (action) {
                case "new":
                    showNewForm(request, response);  // Mostrar formulario para nueva provincia
                    break;
                case "edit":
                    showEditForm(request, response);  // Mostrar formulario para editar provincia
                    break;
                default:
                    listProvinces(request, response);   // Listar todas las provincias
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }


    /**
     * Maneja las solicitudes POST al servlet. Según el parámetro "action", decide qué método invocar.
     * @param request  Solicitud HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException en caso de errores en el servlet.
     * @throws IOException en caso de errores de E/S.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "insert":
                    insertProvince(request, response);  // Insertar nueva provincia
                    break;
                case "update":
                    updateProvince(request, response);  // Actualizar provincia existente
                    break;
                case "delete":
                    deleteProvinces(request, response);  // Eliminar provincia
                    break;
                default:
                    listProvinces(request, response);   // Listar todas las provincias
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }


    /**
     * Lista todas las provincias y las pasa como atributo a la vista `province.jsp`.
     * @param request  Solicitud HTTP.
     * @param response Respuesta HTTP.
     * @throws SQLException en caso de error en la consulta SQL.
     * @throws IOException en caso de error de E/S.
     * @throws ServletException en caso de error en el servlet.
     */
    private void listProvinces(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Province> listProvinces = provinceDAO.listAllProvinces(); // Obtener todas las provincias desde el DAO
        request.setAttribute("listProvinces", listProvinces);      // Pasar la lista de provincias a la vista
        request.getRequestDispatcher("province.jsp").forward(request, response); // Redirigir a la página JSP
    }


    /**
     * Muestra el formulario para crear una nueva provincia.
     * @param request  Solicitud HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException en caso de error en el servlet.
     * @throws IOException en caso de error de E/S.
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Region> listRegions = regionDAO.listAllRegions(); // Obtener la lista de regiones
        request.setAttribute("listRegions", listRegions); // Pasar la lista al JSP
        request.getRequestDispatcher("province-form.jsp").forward(request, response); // Redirigir al JSP
    }



    /**
     * Muestra el formulario para editar una provincia existente.
     * @param request  Solicitud HTTP.
     * @param response Respuesta HTTP.
     * @throws SQLException en caso de error en la consulta SQL.
     * @throws ServletException en caso de error en el servlet.
     * @throws IOException en caso de error de E/S.
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Province existingProvince = provinceDAO.getProvinceById(id);   // Obtener provincia por ID desde el DAO

        // Obtener la lista de regiones para el select
        List<Region> listRegions = regionDAO.listAllRegions();

        request.setAttribute("province", existingProvince);  // Pasar la provincia a la vista
        request.setAttribute("listRegions", listRegions); // Pasar las regiones a la vista
        request.getRequestDispatcher("province-form.jsp").forward(request, response); // Redirigir a la vista para editar


    }


    /**
     * Inserta una nueva provincia en la base de datos después de realizar validaciones.
     * Verifica que el código de la provincias sea único (ignorando mayúsculas) y que los campos
     * de código y nombre no estén vacíos.
     *
     * @param request  la solicitud HTTP con los datos del formulario.
     * @param response la respuesta HTTP para redirigir o mostrar errores.
     * @throws SQLException      si ocurre un error en la base de datos.
     * @throws IOException       si ocurre un error de entrada/salida.
     * @throws ServletException  si ocurre un error en el procesamiento del servlet.
     */
    private void insertProvince(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String code = request.getParameter("code").trim().toUpperCase(); // Convertir a mayúsculas
        String name = request.getParameter("name").trim();
        int idRegion;

        try {
            idRegion = Integer.parseInt((request.getParameter("id_region"))); // Obtén el ID de la región
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID de región no válido.");
            insertProvince(request, response);
            return;
        }

        // Validaciones básicas
        if (code.isEmpty() || name.isEmpty()) {
            request.setAttribute("errorMessage", "El código y el nombre no pueden estar vacíos.");
            request.getRequestDispatcher("province-form.jsp").forward(request, response);
            return;
        }

        // Verifica si el código de la provincia ya existe
        if (provinceDAO.existsProvinceByCode(code)) {
            request.setAttribute("errorMessage", "El código de la provincia ya existe.");
            request.getRequestDispatcher("province-form.jsp").forward(request, response);
            return;
        }

        // Busca la región en el DAO antes de crear la provincia
        Region region = regionDAO.getRegionById(idRegion);
        if (region == null) {
            request.setAttribute("errorMessage", "La región seleccionada no existe.");
            request.getRequestDispatcher("province-form.jsp").forward(request, response);
            return;
        }

        // Crea la nueva provincia
        Province newProvince = new Province(code, name);
        newProvince.setRegion(region); // Asigna la región a la nueva provincia

        // Inserta la provincia en la base de datos
        provinceDAO.insertProvince(newProvince);
        response.sendRedirect("provinces");
    }



    /**
     * Actualiza una provincia existente en la base de datos después de realizar validaciones.
     * Verifica que el código de la provincias sea único para otras provincias (ignorando mayúsculas)
     * y que los campos de código y nombre no estén vacíos.
     *
     * @param request  la solicitud HTTP con los datos del formulario.
     * @param response la respuesta HTTP para redirigir o mostrar errores.
     * @throws SQLException      si ocurre un error en la base de datos.
     * @throws IOException       si ocurre un error de entrada/salida.
     * @throws ServletException  si ocurre un error en el procesamiento del servlet.
     */
    private void updateProvince(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String code = request.getParameter("code").trim().toUpperCase(); // Convertir a mayúsculas
        String name = request.getParameter("name").trim();
        int idRegion = Integer.parseInt(request.getParameter("id_region")); // Obtener el ID de la región

        // Validaciones básicas
        if (code.isEmpty() || name.isEmpty()) {
            request.setAttribute("errorMessage", "El código y el nombre no pueden estar vacíos.");
            request.getRequestDispatcher("province-form.jsp").forward(request, response);
            return;
        }

        // Validar si el código ya existe para otra provincia
        if (provinceDAO.existsProvinceByCodeAndNotId(code, id)) {
            request.setAttribute("errorMessage", "El código de la provincia ya existe para otra provincia.");
            request.getRequestDispatcher("province-form.jsp").forward(request, response);
            return;
        }

        // Buscar la región por su ID
        Region region = regionDAO.getRegionById(idRegion);
        if (region == null) {
            request.setAttribute("errorMessage", "La región seleccionada no existe.");
            request.getRequestDispatcher("province-form.jsp").forward(request, response);
            return;
        }

        // Crear la instancia de Province con los datos actualizados
        Province updatedProvince = new Province(id, code, name, region); // Asegúrate de pasar el ID de la provincia


        try {
            provinceDAO.updateProvince(updatedProvince);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // Código SQL para unique constraint violation
                request.setAttribute("errorMessage", "El código de la provincia debe ser único.");
                request.getRequestDispatcher("province-form.jsp").forward(request, response);
            } else {
                throw e;
            }
        }

        // Redirigir después de la actualización
        response.sendRedirect("provinces");
    }



    /**
     * Elimina una provincias de la base de datos según su ID.
     * @param request  Solicitud HTTP.
     * @param response Respuesta HTTP.
     * @throws SQLException en caso de error en la consulta SQL.
     * @throws IOException en caso de error de E/S.
     */
    private void deleteProvinces(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        provinceDAO.deleteProvince(id);  // Eliminar provincia usando el DAO
        response.sendRedirect("provinces"); // Redirigir al listado de provincias
    }
}
