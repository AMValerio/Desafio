package GoContact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.TimeUnit;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDesafio {
	WebDriver driver;

	/*
	 * @Before 
	 * public void setUp() {
	 * 
	 * System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe"); 
	 * driver = new
	 * ChromeDriver (); }
	 * System.out.println("Abre o chrome");
	 */
	
	@Test
	public void tc1_AlreadyUser() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		//System.setProperty("webdriver.chrome.whitelistedIps", "");
		driver = new ChromeDriver();

		System.out.println("TC01"
				+ " Validar cenário de Criar conta nova: Criar conta nova com user já existente => validar que o site não permite");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");

		// Aceder ao login
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// 1 - Validar cenário de Criar conta nova: Criar conta nova com user já
		// existente => validar que o site não permite
		/*
		 * Dados de teste: email: amevaleriotest@gmail.com pass: Amazon.1
		 */
		
		driver.findElement(By.cssSelector("#createAccountSubmit")).click();
		driver.findElement(By.cssSelector("#ap_customer_name")).sendKeys("Teste01");
		driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
		driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.12");
		driver.findElement(By.cssSelector("#ap_password_check")).sendKeys("Amazon.12");
		driver.findElement(By.cssSelector("#continue")).click();

		WebElement element = driver.findElement(By.cssSelector("#authportal-main-section > div:nth-child(2) > div > div.a-section.a-spacing-large > div > div > h4"));
		String alreadyuser = element.getText();
		System.out.println("Error -> " + alreadyuser);
		assertEquals("E-mail address already in use", alreadyuser);

		driver.quit();

	}

	@Test
	public void tc2_WrongPass() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();

		System.out.println("TC02" + " tentar fazer login com o user e criado mas com password errada => deve falhar");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");

		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// 2 - tentar fazer login com o user e criado mas com password errada => deve
		// falhar
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {

			// Fazer login com o user e criado e com password correta => deve ter sucesso
			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.19"); 
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {
			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.19");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}
				
		WebElement element = driver.findElement(By.cssSelector("#auth-error-message-box > div"));
		String passnok = element.getText();
		System.out.println("Error Messaeg-> " + passnok);
		assertEquals("Your password is incorrect", passnok);
		driver.quit();
	}

	@Test
	public void tc3_loginOK() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();

		System.out.println("TC03" + "Fazer login com o user e criado e com password correta => deve ter sucesso");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");

		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {
			// Fazer login com o user e criado e com password correta => deve ter sucesso
			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {
			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}

		WebElement element = driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1"));
		String loginok = element.getText();
		System.out.println("Login com sucesso -> " + loginok);
		assertNotSame("Sing in", loginok);
		driver.quit();

	}

	@Test
	public void tc4_0_searchBook() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		System.out.println("TC04" + "pesquisar pelo livro: chasing Excellence");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");

		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) 
		{
			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else 
		{
			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}
		
		
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("chasing Excellence + book");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input")).click();

		WebElement element = driver.findElement(By.className("s-search-results"));
		String book = element.getText();
		System.out.println("Esta pesquisa tem resultados ");

		assertNotNull(book);
		assertTrue(book.contains("hasing Excellence"));
		driver.quit();

	}

	@Test
	public void tc4_1_Results() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");
		System.out.println("TC04.1" + " - Confirmar que tenho resultado");
		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}

		// Pesquisar pelo livro: chasing Excellence
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("chasing Excellence + book");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input")).click();

		WebElement element = driver.findElement(By.className("s-search-results"));
		String bookresults = element.getText();
		System.out.println("Esta pesquisa tem resultados ");

		assertNotNull(bookresults);
		assertTrue(bookresults.contains("hasing Excellence"));
		driver.quit();
	}

	@Test
	public void tc4_2_Author() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");
		System.out.println("TC04.2" + " - Procurar pelo nome do autor. Validar se tem no nome Bergeron");

		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}

		// Pesquisar pelo livro: chasing Excellence
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("chasing Excellence + book");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input")).click();

		// (como posso pesquisar pelo nome do autor? e verificar se tem "Bergeron")
		driver.findElement(By.linkText("Chasing Excellence: A Story About Building the World's Fittest Athletes"))
				.click();

		WebElement element = driver.findElement(By.cssSelector(".a-link-normal.contributorNameID"));
		String author = element.getText();

		assertTrue(author.contains("Bergeron"));

		System.out.println("Qual é o nome do autor? O nome do autor é " + author);
		driver.quit();
	}

	@Test
	public void tc4_3CommentsCerith() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");
		System.out.println("TC04.3"
				+ " - Procurar nos comentários: identificar se temos comentário de user: Cerith Leighton Watkins");
		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}

		// Pesquisar pelo livro: chasing Excellence
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("chasing Excellence + book");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input")).click();

		// (Ir para os comentários)
		driver.findElement(By.linkText("Chasing Excellence: A Story About Building the World's Fittest Athletes"))
				.click();
		driver.findElement(By.xpath("//*[@id=\"dp-summary-see-all-reviews\"]/h2")).click();

		boolean found = false;
		boolean hasMorePages = true;
		while (!found && hasMorePages) {
			System.out.println("Dentro do ciclo");
			WebElement element = findElement(By.xpath("//*[@id=\"cm_cr-review_list\"]"));
			if (element != null) {
				// encontrou
				// System.out.println("element " + element);
				String cerith = element.getText();
				// System.out.println("ceritr " + cerith);
				found = cerith.contains("Cerith Leighton Watkins");

				WebElement nextPage = findElement(By.cssSelector("#cm_cr-pagination_bar > ul > li.a-last > a"));
				hasMorePages = nextPage != null;
				System.out.println("fgh" + found + " - hasMorePages: " + hasMorePages);
				if (!found && hasMorePages) {
					nextPage.click();
				}
			} else {
				// nao encontrou
				// WebElement elementButton = findElement(By.cssSelector("#cm_cr-pagination_bar
				// > ul > li.a-disabled.a-last"));
				// hasMorePages = elementButton.getText().contains("Next");
				// vamos parar aqui isto
				hasMorePages = false;
			}

		}

		assertTrue("no more pages and element not found", found);

		// System.out.println("Cerith Leighton Watkins comentou este livo.");
		driver.quit();

	}

	public WebElement findElement(By by) {
		try {
			return driver.findElement(by);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Test
	public void tc4_4InsertComments() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");
		System.out.println("TC04.4" + " - Inserir comentário: \"gostei de ler\"");
		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}
		// Pesquisar pelo livro: chasing Excellence
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("chasing Excellence + book");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input")).click();

		// (Escrever comentário)
		driver.findElement(By.linkText("Chasing Excellence: A Story About Building the World's Fittest Athletes"))
				.click();
		driver.findElement(By.linkText("Write a customer review")).click();

		WebElement element = driver.findElement(By.xpath("//*[@id=\"react-app\"]/div/div/div/div"));
		String insert = element.getText();

		System.out.println("O teste vai falhar pois preciso de um User válido para escrever comentários");
		assertTrue(insert.contains("Write a customer review"));

		driver.quit();

	}

	@Test
	public void tc4_5SearchComments1Star() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");
		System.out.println("TC04.5" + " - Pesquisar por comentários com 1 estrela");
		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}

		// Pesquisar pelo livro: chasing Excellence
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("chasing Excellence + book");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input")).click();

		// (Ir para os comentários)
		driver.findElement(By.linkText("Chasing Excellence: A Story About Building the World's Fittest Athletes"))
				.click();

		// Pesquisar Comentários com uma estreala
		driver.findElement(By.cssSelector("#histogramTable > tbody > tr:nth-child(5) > td:nth-child(1)")).click();

		WebElement element = driver.findElement(By.xpath("//*[@id=\"filter-info-section\"]"));
		String onestar = element.getText();

		assertTrue(onestar.contains("Showing"));
		System.out.println("Existe comentários com uma estrela");

		driver.quit();

	}

	@Test
	public void tc4_6SearchComments1709() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");
		System.out.println("TC04.6" + " - Validar se existe algum comentário com a data de 17 September 2017");
		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}

		// Pesquisar pelo livro: chasing Excellence
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("chasing Excellence + book");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input")).click();

		// (Ir para os comentários)
		driver.findElement(By.linkText("Chasing Excellence: A Story About Building the World's Fittest Athletes"))
				.click();
		driver.findElement(By.xpath("//*[@id=\"dp-summary-see-all-reviews\"]/h2")).click();
		// Validar se existe algum comentário com a data de 17 September 2017

		WebElement element = driver.findElement(By.xpath("//*[@id=\"a-page\"]/div[3]/div[1]/div[2]/div/div[1]"));
		String september = element.getText();

		assertTrue(september.contains("17 September 2017"));
		System.out.println("Existe comentários de 17 de Setembro de 2017");

		driver.quit();

	}

	@Test
	public void tc5_0avengers() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		System.out.println("TC05 " + "Pesquisar pelo filme avengers");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");

		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();
		
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();
		
		if (slogin.contains("Password"))
		{
		
		// Fazer login com o user e criado e com password correta => deve ter sucesso
		driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
		driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
		driver.findElement(By.name("rememberMe")).click();
		driver.findElement(By.cssSelector("#signInSubmit")).click();
		}
		else
		{
		driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
		driver.findElement(By.cssSelector("#continue")).click();
		driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
		driver.findElement(By.name("rememberMe")).click();
		driver.findElement(By.cssSelector("#signInSubmit")).click();
		}
		// Pesquisar pelo Filme: avengers
		//driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("avengers");

		driver.quit();

	}

	@Test
	public void tc5_1avengers() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();

		System.out.println("TC05.1 " + "Pesquisar pelo filme avengers");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");

		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();
		
		if (slogin.contains("Password"))
		{
		
		// Fazer login com o user e criado e com password correta => deve ter sucesso
		driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
		driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
		driver.findElement(By.name("rememberMe")).click();
		driver.findElement(By.cssSelector("#signInSubmit")).click();
		}
		else
		{
		driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
		driver.findElement(By.cssSelector("#continue")).click();
		driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
		driver.findElement(By.name("rememberMe")).click();
		driver.findElement(By.cssSelector("#signInSubmit")).click();
		}

		// Pesquisar pelo Filme: avengers
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("avengers");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right")).click();

		WebElement element = driver
				.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[1]/div[2]"));
		String avengers = element.getText();
		System.out.println("O filme aparece? ");

		assertNotNull(avengers);
		assertTrue(avengers.contains("Avengers Assemble"));

		System.out.println("Sim, o filme aparece na pesquisa");
		driver.quit();

	}

	@Test
	public void tc5_2avengers() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();

		System.out.println("TC05.2 " + "Verificar se a descrição tem a string \"S.H.I.E.L.D\"");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");

		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}

		// Pesquisar pelo Filme: avengers
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("avengers");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right")).click();
		driver.findElement(By.linkText("Avengers Assemble")).click();

		WebElement element = driver
				.findElement(By.xpath("//*[@id=\"a-page\"]/div[3]/div[2]/div/div/div[2]/div[3]/div/div[2]"));
		String shield = element.getText();

		assertNotNull(shield);
		assertTrue(shield.contains("S.H.I.E.L.D"));

		System.out.println("S.H.I.E.L.D aparece na descrição");

		driver.quit();
	}

	@Test
	public void tc5_3avengers() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();

		System.out.println("TC05.3 " + "Watch Trailer - Print Screen");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");

		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}

		// Pesquisar pelo Filme: avengers
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("avengers");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right")).click();
		driver.findElement(By.linkText("Avengers Assemble")).click();
		driver.findElement(
				By.cssSelector("#dv-action-box > div > div > div > div._1y_Ulh.Ri9l84._2WW1HP > div > span > a"))
				.click();
		// driver.findElement(By.xpath("//*[@id=\"dv-action-box\"]/div/div/div/div[2]/div/span/a")).click();
		driver.findElement(By.linkText("Watch")).click();

		System.out.println("Print tirado aos 10s do trailer");
		driver.quit();

	}

	@Test
	public void tc6_0Adidas() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		System.out.println("TC06" + "No Shop by Department: selecionar Sports & Outdoors -> fitness\r\n"
				+ "Procurar nas featured Brands se temos \"Adidas\" -> tirar foto");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");

		// Aceder ao login
		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();

		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}

		// No Shop by Department: selecionar Sports & Outdoors -> fitness
		driver.findElement(By.cssSelector("#searchDropdownBox")).click();
		driver.findElement(By.cssSelector("#searchDropdownBox > option:nth-child(41)")).click();
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("fitness");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input")).click();

		System.out.println("Pesquisa efectuada");
		driver.quit();
	}

	@Test
	public void tc6_1_Adidas() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		System.out.println("TC06.1.1" + " - Procurar nas featured Brands se temos \"Adidas\"");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.co.uk");

		driver.findElement(By.cssSelector("#nav-link-accountList > span.nav-line-1")).click();
		// Fazer login com o user e criado e com password correta => deve ter sucesso
		WebElement login = driver.findElement(By.cssSelector("#authportal-main-section"));
		String slogin = login.getText();

		if (slogin.contains("Password")) {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		} else {

			driver.findElement(By.cssSelector("#ap_email")).sendKeys("amevaleriotest@gmail.com");
			driver.findElement(By.cssSelector("#continue")).click();
			driver.findElement(By.cssSelector("#ap_password")).sendKeys("Amazon.1");
			driver.findElement(By.name("rememberMe")).click();
			driver.findElement(By.cssSelector("#signInSubmit")).click();
		}

		// No Shop by Department: selecionar Sports & Outdoors -> fitness

		WebElement department_dropdown = driver.findElement(By.cssSelector("#searchDropdownBox"));
		Select department = new Select(department_dropdown);
		department.selectByVisibleText("Sports & Outdoors");

		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("fitness");
		driver.findElement(By.cssSelector("#nav-search > form > div.nav-right")).click();

		WebElement element6 = driver.findElement(By.xpath("//*[@id=\"brandsRefinements\"]/ul"));
		String sadidas1 = element6.getText();

		assertFalse(sadidas1.contains("Adidas"));

		System.out.println("Adidas não está presente nas featured Brands");

		driver.quit();

	}
	@Test
	public void tc7_1_AmazonJobs() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		System.out.println("TC07.1" + " - Pesquisar por empregos em \"Portugal, setubal\" => verificar que tenho 1 ou mais");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.jobs/en-gb");
		driver.findElement(By.cssSelector("#search-button")).click(); 
		driver.findElement(By.cssSelector("#location-typeahead")).sendKeys("Portugal, setubal"); 
		driver.findElement(By.cssSelector("#search-container > div > form > button")).click(); 
		
		WebElement element7 = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[3]/div/div/div[2]/content/div")); 
		String	jobs = element7.getText();
		
		assertTrue(jobs.contains("Showing 1 -"));
		System.out.println("Existe um ou mais empregos na sua pesquisa");
		
		driver.quit();

	}
	@Test
	public void tc7_2_AmazonJobs() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver2/chromedriver.exe");
		driver = new ChromeDriver();
		System.out.println("TC07.2" + " - Alterar o filtro para 5 mi => verificar que não tenho nenhum resultado");

		// Entrar no site https://www.amazon.co.uk
		driver.manage().window().maximize();
		driver.navigate().to("https://www.amazon.jobs/en-gb");
		driver.findElement(By.cssSelector("#search-button")).click(); 
		driver.findElement(By.cssSelector("#location-typeahead")).sendKeys("Portugal, setubal"); 
		driver.findElement(By.cssSelector("#search-container > div > form > button")).click(); 
		//driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[3]/div/div/div[2]/content/div/div/div[1]/div/div[6]/div/fieldset/div[2]/button[1]")).click();
		//driver.findElement(By.cssSelector("#main-content > div.search-page > div > div > div.container > content > div > div > div.d-none.d-md-block.col-sm-4.search-page-filter > div > div:nth-child(6) > div > fieldset > div.buttons-group > button.col-xs-2.col-md-4.col-lg-2.btn.btn-in-group.selected")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelector('#main-content > div.search-page > div > div > div.container > content > div > div > div.d-none.d-md-block.col-sm-4.search-page-filter > div > div:nth-child(6) > div > fieldset > div.buttons-group > button.col-xs-2.col-md-4.col-lg-2.btn.btn-in-group.selected').click()");
		 
		 System.out.println("Batatas");
		 
		WebElement element8 = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[3]/div/div/div[2]/content/div")); 
		String	jobs = element8.getText();
		
		assertTrue(jobs.contains("Sorry, there are no jobs"));
		System.out.println("Não existe resultados para o filtro seleccionado");
		
		driver.quit();

	}
}
