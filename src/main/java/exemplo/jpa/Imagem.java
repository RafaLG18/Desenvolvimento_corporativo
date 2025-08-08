/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "TB_IMAGEM")
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Lob
    @Column(name = "BYTE_IMAGE", nullable = false)
    private byte[] image;
    
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Cliente cliente;
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public void setId(Long id){
        this.id=id;
    }
    public void setImagem(byte[] image){
        this.image=image;
    }
    public Long getId(){
        return this.id;
    }
    public byte[] getImage(){
        return this.image;
    }
    public Cliente getCliente() {
        return this.cliente;
    }
}
