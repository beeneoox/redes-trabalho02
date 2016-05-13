/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptoaes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.*;
//import java.security.spec.InvalidKeySpecException;
//import javax.crypto.*;
//import javax.crypto.spec.SecretKeySpec;
import sun.misc.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Arrays;

public class CriptoAES {

    //private static final String Algoritmo = "DES";
    private static final String Algoritmo = "AES";

// metodo para encriptar  utilizando texto e chave passados
    public static String encrypt(String texto, byte[] chave) throws Exception {
        Key key = generateKey(chave); //gera a "key" a partir da chave passada pelo usuário
        Cipher c = Cipher.getInstance(Algoritmo); //cria o algortimo de criptografia no modo escolhido, ex:AES
        c.init(Cipher.ENCRYPT_MODE, key);//inicia o algoritmo no modo encriptação com a chave criada
        //System.out.println(c.getAlgorithm());
        byte[] resultadoEncriptado = c.doFinal(texto.getBytes()); //encripta o texto passado
        String teste = new String(resultadoEncriptado);
        System.out.println("isso em binario " + teste);
        String encriptadoEmBase64 = new BASE64Encoder().encode(resultadoEncriptado); //transforma para Base 64 para poder exibir na tela

        return encriptadoEmBase64;
    }

// metodo para decriptar  utilizando texto encriptado (em base 64) e chave passada
    public static String decrypt(String textoEncriptado, byte[] chave) throws Exception {
        Key key = generateKey(chave); //gera a "key" a partir da chave passada pelo usuário
        Cipher c = Cipher.getInstance(Algoritmo); //cria o algortimo de criptografia no modo escolhido, ex:AES
        c.init(Cipher.DECRYPT_MODE, key); //inicia o algoritmo no modo decriptação com a chave criada
        byte[] decodificadoBase64paraByte = new BASE64Decoder().decodeBuffer(textoEncriptado); //transforma de Base 64 para bytes

        byte[] resultadoDecriptado = c.doFinal(decodificadoBase64paraByte); //decripta valor em byte
        String decriptadoEmString = new String(resultadoDecriptado); //passa vetor de bytes decriptados para string
        return decriptadoEmString;
    }

    //funcao para gerar key de encriptação a partir da chave do usuário
    private static Key generateKey(byte[] chave) throws Exception {
        Key key = new SecretKeySpec(chave, Algoritmo);
        return key;
    }

    public static void main(String[] args) throws Exception {

        StringBuilder stb = new StringBuilder();
        String linha = "";
        String textoEncriptado = "meoIBDSBMYHo3wrkCPqfmnnQNF4Q+QAAP9yc/eTkJd14Teyk19sEoB0WL9IPJ1N0Tpf/+Mj6L4AyR+rGPXntPA==";

        try {
            FileReader arq = new FileReader("C:\\Users\\10070094\\Desktop\\dicionario.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            do {
                linha = lerArq.readLine();
                System.out.println("LINHA: " + linha);
            } while (linha != null);

        } catch (Exception ex) {
            System.out.println("ERROU" + ex.getMessage());
        }

        System.out.println("Texto Encriptado : " + textoEncriptado);
        String senhaIncompleta = "123tinganou";
        stb.append(senhaIncompleta).append(linha);
        System.out.println(stb.toString());

        try {
            byte[] chave2 = senhaIncompleta.getBytes("UTF-8");
            String textoDecriptado = CriptoAES.decrypt(textoEncriptado, chave2);//decripta o texto, passando o texto encriptado e a chave
            System.out.println("Texto Decriptado: " + textoDecriptado);

        } catch (Exception ex) {
            System.out.println("ERROU! Catch");
        }
    }

}
