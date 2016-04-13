package com.dane.ige.mail;

import java.util.Map;

/**
 * Clase que representa un objeto de tipo correo electronico o mail.
 *
 * @author SRojasM
 */
public class Mail {

    private Map<Integer, String> destino;
    private String titulo;
    private String mensagem;

    public Map<Integer, String> getDestino() {
        return destino;
    }

    public void setDestino(Map<Integer, String> destino) {
        this.destino = destino;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
