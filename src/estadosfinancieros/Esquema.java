/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package estadosfinancieros;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 *
 * @author sxceron
 */
@Entity
public class Esquema implements Serializable {
    @ManyToOne
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Cuenta cuenta;
    @OneToOne
    private Balance balance;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "esquema", fetch = FetchType.EAGER)
    private List<Cargo> cargos = new ArrayList<Cargo>();
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "esquema", fetch = FetchType.EAGER)
    private List<Abono> abonos = new ArrayList<Abono>();

    public List<Abono> getAbonos() {
        return abonos;
    }

    public void setAbonos(List<Abono> abonos) {
        this.abonos = abonos;
    }


    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }


    public Cuenta getCuenta() {
        return cuenta;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Transient
    public Double getSaldo(){
        Double deudor = 0.0;
        Double acredor = 0.0;
        try{
            for( Cargo c : getCargos() )
                deudor += c.getCantidad();
            for( Abono a : getAbonos() )
                acredor += a.getCantidad();
        }catch(Exception e){

        }
        return deudor > acredor ? deudor - acredor : acredor - deudor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Esquema)) {
            return false;
        }
        Esquema other = (Esquema) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "estadosfinancieros.Esquema[id=" + id + "]";
    }

}
