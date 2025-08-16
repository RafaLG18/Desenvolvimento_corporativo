/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.util.Date;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "TB_PEDIDO")
@NamedQueries(
        {
            @NamedQuery(
                    name = "UltimoPedido",
                    query = "SELECT MAX(p.data), MIN(p.data) FROM Pedido p"
            )
        }
)
public class Pedido {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="TXT_TIPO_PAGAMENTO",nullable=false,length=20)
    private TipoPagamento tipo;
    @Past
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_PEDIDO", nullable = false)
    private Date data;
    @NotNull
    @OneToOne(mappedBy="pedido")
    private Cliente cliente;
    
    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    
    public void setData(Date data){
        this.data=data;
    }
    public Date getData(){
        return this.data;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    public Cliente getCliente(){
        return this.cliente;
    }
    
    public TipoPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamento tipo) {
        this.tipo = tipo;
    }
    
    
}
