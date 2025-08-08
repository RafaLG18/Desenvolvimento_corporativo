/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafael
 */
@Entity
@Table(name="TB_CLIENTE")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Cliente.Pedido",
                    query = "SELECT c FROM Cliente c WHERE c.pedido.tipo LIKE :tipo"
            )
        }
)
@DiscriminatorValue(value = "Cliente")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class Cliente extends Usuario{
    
    @OneToMany(fetch=FetchType.LAZY, cascade= CascadeType.ALL, mappedBy = "cliente")
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID" )
    private List<Imagem> imagens;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_PEDIDO", referencedColumnName = "ID")
    private Pedido pedido;
    
    public List<Imagem> getImages() {
        return imagens;
    }

    public void addImage(Imagem image) {
        if(imagens==null){
            imagens=new ArrayList<>();
        }
        this.imagens.add(image);
        image.setCliente(this);
    }
    
    public void setPedido(Pedido pedido){
        this.pedido=pedido;
        if(this.pedido!=null){
           this.pedido.setCliente(this);
        }
    }
    public Pedido getPedido(){
        return this.pedido;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("exemplo.jpa.Usuario[");
        sb.append(this.imagens);
        sb.append(this.pedido);
        
        sb.append("]");
        return sb.toString();
    }
    
}
