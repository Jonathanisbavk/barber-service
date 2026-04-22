package com.barbershop.barber_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class SaludoLangChainService {

    private static final String SUFIJO_EMOJI = " 🙂";
    private static final int LONGITUD_OBJETIVO = 100;
    private final String openAiApiKey;

    public SaludoLangChainService(@Value("${openai.api.key:}") String openAiApiKey) {
        this.openAiApiKey = openAiApiKey;
    }

    public String generarSaludoInicio(String nombreUsuario) {
        String nombre = (nombreUsuario == null || nombreUsuario.isBlank()) ? "cliente" : nombreUsuario.trim();
        int variante = ThreadLocalRandom.current().nextInt(1, 10000);

        String prompt = "Genera un saludo para inicio de frontend de barberia, tono informal, en espanol, de exactamente 100 caracteres y terminando con emoji. " +
                "Nombre: " + nombre + ". Usa una redaccion diferente en cada ejecucion. Variante: " + variante + ". " +
                "Devuelve solo el saludo final, sin comillas ni texto extra.";

        if (openAiApiKey == null || openAiApiKey.isBlank()) {
            return ajustarLongitudConEmoji(fallbackAleatorio(nombre));
        }

        String generado = generarConLangChain(prompt);
        if (generado == null || generado.isBlank()) {
            generado = fallbackAleatorio(nombre);
        }

        return ajustarLongitudConEmoji(generado);
    }

    private String fallbackAleatorio(String nombre) {
        String[] plantillas = new String[] {
                "Que tal " + nombre + ", hoy toca corte con flow. Entra, relajate y sal con estilo de primera",
                "Hey " + nombre + ", bienvenido. Arrancamos con buena vibra para dejarte impecable hoy mismo",
                "Hola " + nombre + ", pasa con confianza. Te dejamos fresh y listo para romperla donde vayas",
                nombre + ", que gusto verte por aqui. Vamos por un look limpio, moderno y bien pro hoy",
                "Bienvenido " + nombre + ", ponte comodo. Hoy sales con corte brutal y actitud al maximo"
        };
        int indice = ThreadLocalRandom.current().nextInt(plantillas.length);
        return plantillas[indice];
    }

    private String generarConLangChain(String prompt) {
        try {
            Class<?> openAiModelClass = Class.forName("dev.langchain4j.model.openai.OpenAiChatModel");
            Object builder = openAiModelClass.getMethod("builder").invoke(null);

            builder = builder.getClass().getMethod("apiKey", String.class).invoke(builder, openAiApiKey);
            builder = builder.getClass().getMethod("modelName", String.class).invoke(builder, "gpt-4o-mini");

            Object model = builder.getClass().getMethod("build").invoke(builder);
            Object respuesta = model.getClass().getMethod("generate", String.class).invoke(model, prompt);
            return respuesta == null ? null : respuesta.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String ajustarLongitudConEmoji(String texto) {
        String limpio = texto == null ? "" : texto.replace("\n", " ").trim();
        limpio = limpio.replaceAll("\\s+", " ");

        if (limpio.endsWith("🙂")) {
            limpio = limpio.substring(0, limpio.length() - "🙂".length()).trim();
        }

        int objetivoSinSufijo = LONGITUD_OBJETIVO - SUFIJO_EMOJI.length();
        if (limpio.length() > objetivoSinSufijo) {
            limpio = limpio.substring(0, objetivoSinSufijo).trim();
        }

        if (limpio.length() < objetivoSinSufijo) {
            limpio = (limpio + " ").repeat((objetivoSinSufijo / Math.max(1, (limpio + " ").length())) + 2).substring(0, objetivoSinSufijo).trim();
            while (limpio.length() < objetivoSinSufijo) {
                limpio = limpio + "!";
            }
        }

        return limpio.substring(0, objetivoSinSufijo) + SUFIJO_EMOJI;
    }
}

