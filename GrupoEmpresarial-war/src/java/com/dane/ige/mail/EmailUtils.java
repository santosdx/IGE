package com.dane.ige.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * Clase (ManagedBean) que contiene la funcionalidad del negocio para la
 * implementación de los envios de correo electronico institucional.
 *
 * @author SRojasM
 */
@ManagedBean(name = "MBemailUtils")
@SessionScoped
public class EmailUtils implements Serializable {

    final static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(EmailUtils.class);
    private Properties properties = new Properties();

    private String HOSTNAME = "";
    private int PORT = 0;
    private String USERNAME = "";
    private String PASSWORD = "";
    private String EMAILORIGEM = "";

    public EmailUtils() {
    }

    @PostConstruct
    public void init() {
        //LOGGER.info("Mail...");
    }

    /**
     * Método que permite conectarse al servidor de correo y obtener un ojbeto
     * de tipo Email con la configuración del servidor para permitir enviar el
     * correo.
     *
     * @return
     * @throws EmailException
     */
    private Email conectaEmail() throws EmailException {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sistema.properties");
        try {
            properties.load(inputStream);
        } catch (IOException ex) {
            LOGGER.warn(ex);
            //java.util.logging.Logger.getLogger(EmailUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        HOSTNAME = properties.getProperty("sistema.email.hostname");
        PORT = Integer.parseInt(properties.getProperty("sistema.email.port"));
        USERNAME = properties.getProperty("sistema.email.username");
        PASSWORD = properties.getProperty("sistema.email.password");
        EMAILORIGEM = properties.getProperty("sistema.email.emailorigen");

        Email email = new SimpleEmail();
        //email.setDebug(true);
        email.setHostName(HOSTNAME);
        email.setSmtpPort(PORT);
        email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
        //email.setSSLOnConnect(true);
        email.setFrom(EMAILORIGEM);
        return email;
    }

    /**
     * Método que permite enviar un correo electronico y recibe como parametro
     * el objeto tipo Mail con la información.
     *
     * @param mensagem
     * @throws EmailException
     */
    private void envioFinalEmail(Mail mensagem) throws EmailException {
        Email email = new SimpleEmail();
        email = conectaEmail();
        email.setFrom(EMAILORIGEM, "SID");
        email.setSubject(mensagem.getTitulo());
        email.setMsg(mensagem.getMensagem());
        // Imprimimos el Map con un Iterador
        Iterator it = mensagem.getDestino().keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            email.addTo(mensagem.getDestino().get(key).toString());
            //LOGGER.info(mensagem.getDestino().get(key).toString());
        }
        //String resposta = email.send();
        email.send();
    }

    /**
     * Método que permite conectarse al servidor de correo y obtener un ojbeto
     * de tipo HtmlEmail con la configuración del servidor para permitir enviar
     * el correo.
     *
     * @return
     * @throws EmailException
     */
    private HtmlEmail conectaHtmlEmail() throws EmailException {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sistema.properties");
        try {
            properties.load(inputStream);
        } catch (IOException ex) {
            LOGGER.warn(ex);
            //java.util.logging.Logger.getLogger(EmailUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        HOSTNAME = properties.getProperty("sistema.email.hostname");
        PORT = Integer.parseInt(properties.getProperty("sistema.email.port"));
        USERNAME = properties.getProperty("sistema.email.username");
        PASSWORD = properties.getProperty("sistema.email.password");
        EMAILORIGEM = properties.getProperty("sistema.email.emailorigen");
        //System.out.println(HOSTNAME + " " + PORT + " " + USERNAME + " " + PASSWORD + " " + EMAILORIGEM);
        HtmlEmail email = new HtmlEmail();
        //email.setDebug(true);
        email.setHostName(HOSTNAME);
        email.setSmtpPort(PORT);
        email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
        //email.setSSLOnConnect(true);
        email.setFrom(EMAILORIGEM);
        return email;
    }

    /**
     * Método que permite enviar un correo electronico con formato HTML, recibe
     * como parametro el objeto tipo HtmlEmail con la información.
     *
     * @param titulo
     * @param cuerpo
     * @param destinatarios
     * @throws EmailException
     */
    public void envioFinalHtmlEmail(String titulo, String cuerpo, Map<Integer, String> destinatarios) throws EmailException {
        try {
            HtmlEmail email = new HtmlEmail();
            email = conectaHtmlEmail();
            email.setFrom(EMAILORIGEM, "SID");
            email.setSubject(titulo);
            email.setHtmlMsg(cuerpo);
            //email.setDebug(true);
            // Imprimimos el Map con un Iterador
            Iterator it = destinatarios.keySet().iterator();
            while (it.hasNext()) {
                Integer key = (Integer) it.next();
                email.addTo(destinatarios.get(key).toString());
                //LOGGER.info(mensagem.getDestino().get(key).toString());
            }
            //String resposta = email.send();
            email.send();
        } catch (EmailException ex) {
            java.util.logging.Logger.getLogger(EmailUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que permite generar el texto de saludo dependiendo de la hora del
     * día para ser enviado el correo.
     *
     * @return String
     */
    public String obtenerSaludoMensaje() {
        String saludo = "";
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        if (date.getHours() >= 0 && date.getHours() < 12) {
            saludo = "Buenos días";
        } else if (date.getHours() >= 12 && date.getHours() < 21) {
            saludo = "Buenas tardes";
        } else if (date.getHours() >= 21 && date.getHours() < 24) {
            saludo = "Buenas noches";
        } else {
            saludo = "Hora no válida";
        }

        return saludo;
    }

    /**
     * Método para pruebas.
     */
    public void test() {
        LOGGER.info("Test...");
    }

}
