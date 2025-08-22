package exemplo.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/*
 * Todos os campos de Endereco serão armazenados na mesma tabela 
 * que armazena os dados de Usuario.
 */
@Embeddable
public class Endereco {
    @NotBlank
    @Size(max = 150)
    @Column(name = "END_TXT_LOGRADOURO", length = 150, nullable = false)
    private String logradouro;
    @NotBlank
    @Size(max = 150)
    @Column(name = "END_TXT_BAIRRO", length = 150, nullable = false)
    private String bairro;
    @NotNull
    @Min(1)
    @Max(99999)
    @Column(name = "END_NUMERO", length = 5, nullable = false)
    private Integer numero;
    @Size(max = 30)
    @Column(name = "END_TXT_COMPLEMENTO", length = 30, nullable = true)
    private String complemento;
    @NotNull
    @ValidaCep
    @Column(name = "END_TXT_CEP", length = 20, nullable = false)
    private String cep;
    @NotBlank
    @Size(max = 50)
    @Column(name = "END_TXT_CIDADE", length = 50, nullable = false)
    private String cidade;
    @NotBlank
    @ValidaEstado
    @Size(min = 2, max = 2)
    @Column(name = "END_TXT_ESTADO", length = 50, nullable = false)
    private String estado;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
