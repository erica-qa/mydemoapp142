package steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class ComprarProduto {

    private AndroidDriver driver;

    private URL getUrl() {
        try {
            return new URL(
                    "https://Erica:0364848a-1202-419a-a880-68b27838c6d3@ondemand.us-west-1.saucelabs.com:443/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Before
    public void Iniciar() {
        var options = new BaseOptions()
                .amend("platformName", "Android")
                .amend("appium:platformVersion", "9.0")
                .amend("appium:deviceName", "Samsung Galaxy S9 FHD GoogleAPI Emulator")
                .amend("appium:deviceOrientation", "portrait")
                .amend("appium:app", "storage:filename=mda-2.2.0-25.apk")
                .amend("appium:appPackage", "com.saucelabs.mydemoapp.android")
                .amend("appium:appActivity", "com.saucelabs.mydemoapp.android.view.activities.SplashActivity")
                .amend("appium:automationName", "UiAutomator2")
                .amend("browserName", "")
                .amend("appium:ensureWebviewsHavePages", true)
                .amend("appium:nativeWebScreenshot", true)
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);

        driver = new AndroidDriver(this.getUrl(), options); // esta linha instancia o Appium e abre o App
    }

    @After
    public void finalizar() throws InterruptedException {
        driver.quit();
        Thread.sleep(10000);
    }

    @Dado("que acesso o My Demo App")
    public void que_acesso_o_my_demo_app() {
        // App foi aberto no final do método iniciar (Before)
    }

    @E("verifico o logo e o nome da secao")
    public void verifico_o_logo_e_o_nome_da_secao() {
        // logo:
        var imgLogo = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/mTvTitle"));
        assertEquals(imgLogo.isDisplayed(), true);

        // secao:
        var lblTituloSecao = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV"));
        assertEquals("Products", lblTituloSecao.getText());
    }

    @E("localizo o {string} que esta na posicao {int} por {string}")
    public void localizo_o_que_esta_por(String produto, Integer num_produto, String preco) {
        // Home
        // produto :
        // preco :
        assertEquals(produto, driver.findElement(AppiumBy.xpath(
                "//android.widget.TextView[@content-desc=\"Product Title\" and @text=\"" + produto + "\"]")).getText());

        assertEquals(preco,
                driver.findElement(AppiumBy
                        .xpath("(//android.widget.TextView[@content-desc=\"Product Price\"])[" + num_produto + "]"))
                        .getText());
    }

    @Quando("clico na imagem do {int}")
    public void clico_na_imagem_do(Integer num_produto) {
        driver.findElement(
                AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"Product Image\"])[" + num_produto + "]"))
                .click();
    }

    @Entao("na tela do produto verifico o {string} e o {string}")
    public void na_tela_do_produto_verifico_o_e_o(String produto, String preco) {
        assertEquals(produto,
                driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV")).getText());
        assertEquals(preco, driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/priceTV")).getText());
    }

    @Quando("arrasto para cima e clico no botao Add Cart")
    public void arrasto_para_cima_e_clico_no_botao_add_cart() {
        // Tela do Produto:
        // botão adicionar no carrinho
        // Arrastar para cima
        final var finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        var start = new Point(525, 1698);
        var end = new Point(530, 563);
        var swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), start.getX(), start.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(), end.getX(), end.getY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));

        // Clicar no botão add to chart
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt")).click();

        // Verificar se o número de produtos no carrinho foi atualizado
        assertEquals("1", driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartTV")).getText());

        // Clicar no ícone do carrinho de compras para ir para a sua tela
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartIV")).click();
    }

    @Entao("na tela do carrinho verifico o {string} {string} e {int}")
    public void na_tela_do_carrinho_verifico_o_produto_preco_e_quantidade(String produto, String preco,
            Integer quantidade) {
        // Carrinho:
        // produto:
        // preço:
        // quantidade:

        // Verificar o titulo da seção
        assertEquals("Cart", driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/titleTV")).getText());

        // Verificar o titulo do produto
        assertEquals(produto,
                driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV")).getText());

        // Verificar o preço do produto
        assertEquals(preco, driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/priceTV")).getText());

        // Verificar a quantidade
        assertEquals(quantidade,
                driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/itemsTV")).getText());

        // Verificar o preço total
        assertEquals(preco,
                driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/totalPriceTV")).getText());
    }
}
