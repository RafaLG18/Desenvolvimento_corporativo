/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste;

import exemplo.jpa.Cliente;
import exemplo.jpa.Imagem;
import exemplo.jpa.Servico;
import static exemplo.jpa.teste.Teste.logger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 *
 * @author rafael
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImagemTeste extends Teste{
    static int lenthImagem;
    @Test
    public void ApersistirImagem() throws FileNotFoundException, IOException{
        Imagem image=new Imagem();
        
        Cliente cliente = em.find(Cliente.class, 1L);
        FileInputStream fin = new FileInputStream("/home/rafael/Downloads/Teste.png");
        
        image.setImagem(fin.readAllBytes());
        lenthImagem=image.getImage().length;
        
        cliente.addImage(image);
        
        em.persist(image);
        em.flush();
        
        assertNotNull(image.getId());
        assertNotNull(cliente.getId());
    }
    
    @Test
    @SuppressWarnings("empty-statement")
    public void BconsultarImagem() throws FileNotFoundException, IOException{
        FileInputStream fin = new FileInputStream("/home/rafael/Downloads/Teste.png");
        
        
        Imagem imagem=em.find(Imagem.class, 2L);
        byte[] imagemFin=fin.readAllBytes();
        assertEquals(imagem.getImage().length,lenthImagem);
        
        for (int i = 0; i < imagemFin.length; i++) {
            assertTrue(imagemFin[i] == imagem.getImage()[i]);
            
        }
        em.clear();
        
    }
    
    @Test
    @SuppressWarnings("empty-statement")
    public void CconsultarImagemByte() throws FileNotFoundException, IOException{
        FileInputStream fin = new FileInputStream("/home/rafael/Downloads/Teste.png");
        
        Imagem imagem=em.find(Imagem.class, 2L);
        
        byte[] imagemFin=fin.readAllBytes();
//        assertEquals(imagem.getImage().length,lenthImagem);
        
        for (int i = 0; i < imagemFin.length; i++) {
            assertTrue(imagemFin[i] == imagem.getImage()[i]);
            
        }
        
        
    }
    
     @Test
    public void DatualizaImagem() throws FileNotFoundException, IOException {
        logger.info("Executando atualizaServico()");
        FileInputStream fin = new FileInputStream("/home/rafael/Downloads/Teste2.jpeg");
        Imagem imagem = em.find(Imagem.class, 2L);
        imagem.setImagem(fin.readAllBytes());
        em.flush();
        em.clear(); // Limpa a cache para buscar no banco.
        Imagem imagemVerificado = em.find(Imagem.class, 2L);
        
        byte[] imagemFin=fin.readAllBytes();
        
        for (int i = 0; i < imagemFin.length; i++) {
            assertTrue(imagemFin[i] == imagemVerificado.getImage()[i]);
            
        }
    }

    @Test
    public void EatualizaImagemMerge() throws FileNotFoundException, IOException {
        logger.info("Executando atualizaServicoMerge()");
        
        Imagem imagem = em.find(Imagem.class, 2L);
        assertNotNull(imagem);

        em.clear(); 
        FileInputStream fin = new FileInputStream("/home/rafael/Downloads/Teste3.jpg");
        imagem.setImagem(fin.readAllBytes());
        
        Imagem imagemGerenciado = em.merge(imagem); // Retorna uma cópia gerenciada

        em.flush(); // Força a atualização no banco de dados
        em.clear(); // Limpa a cache para buscar no banco. 
        imagem = em.find(Imagem.class, imagem.getId());
        
        
        byte[] imagemFin=fin.readAllBytes();
        
        for (int i = 0; i < imagemFin.length; i++) {
            assertTrue(imagemFin[i] == imagem.getImage()[i]);
            
        }
    }
    
    @Test
    public void FremoveImagem(){
        logger.info("Executando removeServico()");
        Imagem imagem = em.find(Imagem.class, 1L);
        em.remove(imagem);
        em.flush();
//        em.clear();
        Imagem imagemApagado = em.find(Imagem.class, 1L);
        assertNull(imagemApagado);  
    }
    
}
