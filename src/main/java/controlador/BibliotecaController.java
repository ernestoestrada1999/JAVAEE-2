package controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Libro;
import modelo.LibroDAO;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.time.zone.ZoneRulesException;
import java.util.ArrayList;

/**
 * Servlet implementation class BibliotecaController
 */
@WebServlet(urlPatterns = {"","/insertar"})
public class BibliotecaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

    public BibliotecaController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet BibliotecaController");
		RequestDispatcher despachador=null;
		if(request.getServletPath().equals("")) {
		
			try {
				LibroDAO libroDAO=new LibroDAO();
				ArrayList<Libro>libros;
				libros=libroDAO.getLibros();
				request.setAttribute("libros", libros);	
		
			}catch (RuntimeException e) {
				request.setAttribute("error", e.getMessage());
			}
			despachador=request.getServletContext().getRequestDispatcher("/index.jsp");
			
		}else if(request.getServletPath().equals("/insertar")) {
			try {
				LibroDAO libroDAO=new LibroDAO();
				Libro libro= new Libro(Integer.parseInt(request.getParameter("isbn")),request.getParameter("titulo")
						,request.getParameter("autor"));
				libroDAO.insertar(libro);
				request.setAttribute("info",libro+" añadido");
			}catch (NumberFormatException e) {
				request.setAttribute("error", e);
			}catch (RuntimeException e) {
				request.setAttribute("error", e.getMessage());
			}
			despachador=request.getServletContext().getRequestDispatcher("/");
		}
		despachador.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
