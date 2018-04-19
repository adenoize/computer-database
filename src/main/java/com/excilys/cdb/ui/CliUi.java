package main.java.com.excilys.cdb.ui;

import java.util.Scanner;

import main.java.com.excilys.cdb.controller.CDBController;
import main.java.com.excilys.cdb.enums.CompanyChoice;
import main.java.com.excilys.cdb.enums.ComputerChoice;
import main.java.com.excilys.cdb.enums.DAOType;
import main.java.com.excilys.cdb.enums.MenuChoice;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.utils.Page;

/**
 * Interface utilisateur CLI Regroupe toutes les fonctions d'affichage
 *
 */

public class CliUi {

	/**
	 * Scanner pour lire les entrées clavier
	 */
	private Scanner scanner;

	/**
	 * Booléen pour rester ou non dans le menu principal
	 */
	private boolean whileMenu = true;

	/**
	 * Booléen pour rester ou non dans le menu des computers
	 */
	private boolean computerWhile = true;

	/**
	 * Booléen pour rester ou non dans le menu des company
	 */
	private boolean companyWhile = true;

	/**
	 * La page actuelle de l'utilisateur
	 */
	private String currentPage = "0";

	/**
	 * Controleur
	 */
	private CDBController controller;

	
	private final String R_NUMBER = "[0-9]+";
	private final String R_TEXT = "[a-zA-Z-0-9]+";
	private final String R_DATE = "^((18|19|20|21)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
	
	
	/**
	 * Constructeur pour attribuer le controler, le scanner et message d'arrivé
	 * 
	 * @param cdbController
	 *            le controleur de l'application
	 */
	public CliUi(CDBController cdbController) {
		controller = cdbController;
		scanner = new Scanner(System.in);
		System.out.println("###################");
		System.out.println("######WELCOME######");
		System.out.println("###################");
		System.out.println();
		goToMenu();
	}

	/**
	 * Affiche le menu de l'application Redirige en fonction des actions choisies:
	 * MenuChoice
	 */
	public void goToMenu() {
		while (whileMenu) {
			System.out.println("#############");
			System.out.println("####MENU####");
			System.out.println("#############");
			System.out.println();
			System.out.println(MenuChoice.LISTCOMPUTER);
			System.out.println(MenuChoice.LISTCOMPANY);
			System.out.println(MenuChoice.QUIT);
			MenuChoice input = null;
			do {
				input = MenuChoice.get(scanner.nextLine());
			} while (input == null);
			switch (input) {
			case LISTCOMPUTER:
				showListComputers(controller.getComputers(currentPage));
				break;
			case LISTCOMPANY:
				showListCompanies(controller.getCompanies(currentPage));
				break;
			case QUIT:
				goToEnd();
				whileMenu = false;
				break;

			}
		}
	}

	/**
	 * Affiche la liste des computers et le sous-menu des computers Redirige en
	 * fonction des actions choisies: ComputerChoice
	 * 
	 * @param computers
	 *            la liste des computers
	 */
	public void showListComputers(Page<Computer> computers) {
		System.out.println("###############");
		System.out.println("#COMPUTER MENU#");
		System.out.println("###############");
		System.out.println();
		for (Computer computer : computers.getResults()) {
			System.out.println(computer);
		}
		System.out.println();
		while (computerWhile) {
			System.out.println("#############");
			System.out.println("###ACTIONS###");
			System.out.println("#############");
			System.out.println();
			System.out.println(ComputerChoice.SELECTPAGE);
			System.out.println(ComputerChoice.DETAILSCOMPUTER);
			System.out.println(ComputerChoice.CREATECOMPUTER);
			System.out.println(ComputerChoice.UPDATECOMPUTER);
			System.out.println(ComputerChoice.DELETECOMPUTER);
			System.out.println(ComputerChoice.BACKMENU);
			ComputerChoice input = null;
			do {
				input = ComputerChoice.get(scanner.nextLine());
			} while (input == null);
			switch (input) {
			case SELECTPAGE:
				String page = selectPage(DAOType.COMPUTER);
				currentPage = page;
				Page<Computer> new_page = controller.getComputers(currentPage);
				showListComputers(new_page);
				break;
			case CREATECOMPUTER:
				createComputer();
				break;
			case UPDATECOMPUTER:
				updateComputer();
				break;
			case DELETECOMPUTER:
				deleteComputer();
				break;
			case DETAILSCOMPUTER:
				showDetailsComputer();
				break;
			case BACKMENU:
				currentPage = "0";
				goToMenu();
			default:
				computerWhile = false;
				break;
			}
		}
	}

	/**
	 * Affiche la page max et courante
	 * @param type le type d'objet voulu
	 * @return la page sélectionnée 
	 */
	private String selectPage(DAOType type) {
		System.out.println("Your page: " + currentPage);
		System.out.println("Max page: " + controller.getMaxPage(type));
		String page;
		do {
			page = scanner.nextLine();
		} while (!page.matches(R_NUMBER));
		return page;
	}

	/**
	 * Affiche la liste des company Retourne sur le menu principal
	 * 
	 * @param companies
	 *            la liste des company
	 */
	public void showListCompanies(Page<Company> companies) {
		while (companyWhile) {
			System.out.println("###############");
			System.out.println("#COMPANY MENU#");
			System.out.println("###############");
			System.out.println();
			for (Company company : companies.getResults()) {
				System.out.println(company);
			}
			System.out.println();
			System.out.println(CompanyChoice.SELECTPAGE);
			System.out.println(CompanyChoice.BACK);
			CompanyChoice input = null;
			do {
				input = CompanyChoice.get(scanner.nextLine());
			} while (input == null);
			switch (input) {
			case SELECTPAGE:
				String page = selectPage(DAOType.COMPANY);
				currentPage = page;
				Page<Company> new_page = controller.getCompanies(currentPage);
				showListCompanies(new_page);
				break;
			case BACK:
				currentPage = "0";
				goToMenu();
			default:
				companyWhile = false;
				break;
			}
		}
	}

	/**
	 * Affiche les détails d'un computer
	 */
	public void showDetailsComputer() {
		System.out.println("##################");
		System.out.println("#COMPUTER DETAILS#");
		System.out.println("##################");
		System.out.println();
		System.out.println("Computer id:");
		String id;
		do {
			id = scanner.nextLine();
		} while (!id.matches(R_NUMBER));
		Computer computer = controller.getComputerDetails(id);
		System.out.println(computer);
	}

	/**
	 * Affichage de création d'un computer
	 */
	public void createComputer() {
		System.out.println("#################");
		System.out.println("#CREATE COMPUTER#");
		System.out.println("#################");
		System.out.println();
		System.out.println("Tap ENTER if you don't want to specify this filed");
		System.out.println("Name:");
		String s, name, intro, disco, company_id;
		do {
			s = scanner.nextLine();
		} while (!s.matches(R_TEXT));
		name = s;
		System.out.println("Introduced date:");
		do {
			s = scanner.nextLine();
		} while (!s.matches(R_DATE) && !s.equals(""));
		intro = s;
		System.out.println("Discontinued date:");
		do {
			s = scanner.nextLine();
		} while (!s.matches(R_DATE) && !s.equals(""));
		disco = s;
		System.out.println("Company id:");
		do {
			s = scanner.nextLine();
		} while (!s.matches(R_NUMBER) && !s.equals(""));
		company_id = s;
		if (controller.createComputer(name, intro, disco, company_id)) {
			System.out.println("CREATION EFFECTUEE");
		} else {
			System.out.println("CREATION NON EFFECTUEE");
		}
	}

	/**
	 * Affichage de mise à jour d'un computer
	 */
	public void updateComputer() {
		System.out.println("#################");
		System.out.println("#UPDATE COMPUTER#");
		System.out.println("#################");
		System.out.println();
		System.out.println("Tap ENTER if you don't want to specify this filed");
		System.out.println("Computer id:");
		String s, computer_id, name, intro, disco, company_id;
		do {
			do {
				s = scanner.nextLine();
			} while (!s.matches(R_NUMBER));
		} while (!controller.isComputer(s));
		System.out.println("COMPUTER " + s);
		System.out.println(controller.getComputerDetails(s));
		computer_id = s;
		System.out.println("Name:");
		do {
			s = scanner.nextLine();
		} while (!s.matches(R_TEXT));
		name = s;
		System.out.println("Introduced date:");
		do {
			s = scanner.nextLine();
		} while (!s.matches(R_DATE) && !s.equals(""));
		intro = s;
		System.out.println("Discontinued date:");
		do {
			s = scanner.nextLine();
		} while (!s.matches(R_DATE) && !s.equals(""));
		disco = s;
		System.out.println("Company id:");
		do {
			s = scanner.nextLine();
		} while (!s.matches(R_NUMBER) && !s.equals(""));
		company_id = s;
		if (controller.updateComputer(computer_id, name, intro, disco, company_id)) {
			System.out.println("MISE A JOUR EFFECTUEE");
		} else {
			System.out.println("MISE A JOUR NON EFFECTUEE");
		}
	}

	/**
	 * Affichage de suppression d'un computer
	 */
	public void deleteComputer() {
		System.out.println("#################");
		System.out.println("#DELETE COMPUTER#");
		System.out.println("#################");
		System.out.println();
		System.out.println("Computer id:");
		String s = null;
		do {
			s = scanner.nextLine();
		} while (!s.matches(R_NUMBER));
		controller.deleteCompute(s);
		System.out.println("SUPPRESION EFFECTUEE");
	}

	/**
	 * Affichage de fin
	 */
	public void goToEnd() {
		scanner.close();
		System.out.println("###################");
		System.out.println("########BYE########");
		System.out.println("###################");
	}

}
