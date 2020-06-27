/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Romario
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByFecharegistro", query = "SELECT u FROM Usuario u WHERE u.fecharegistro = :fecharegistro"),
    @NamedQuery(name = "Usuario.findByCargo", query = "SELECT u FROM Usuario u WHERE u.cargo = :cargo")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "nombre")
    private String nombre;
    @Id
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "fecharegistro")
    @Temporal(TemporalType.DATE)
    private Date fecharegistro;
    @Column(name = "cargo")
    private String cargo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Agenda> agendaList;

    public Usuario() {
    }

    public Usuario(String email) {
        this.email = email;
    }
    
    public Usuario(String nombre, String email, String password, String cargo, Date fechaRegistro) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fecharegistro = fechaRegistro;
        this.cargo = cargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @XmlTransient
    public List<Agenda> getAgendaList() {
        return agendaList;
    }

    public void setAgendaList(List<Agenda> agendaList) {
        this.agendaList = agendaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Usuario[ email=" + email + " ]";
    }
    
    public String agendasToHtmlFormat(int agendaSelecionada){
        String agendas="";
        String classStyle = "class='seleccionada'";
        if(!getAgendaList().isEmpty()){
            for(Agenda a:getAgendaList()){
                if(agendaSelecionada==a.getAgendaPK().getId()){
                    agendas += "<li "+classStyle+">"
                    + "<form  action='ir_agenda_seleccionada.do' method='POST' >"
                    + "<input type='text' name='agenda' style='display:none;' value='"+a.getAgendaPK().getId()+"' required/>"
                    + "<button type='submit'name='selccionada'><i class='fas fa-book'></i><p>"+a.getNombre()+"</p></button>"
                    + "</form>"
                    + "</li>";
                }else{
                    agendas += "<li class='agenda'>"
                    + "<form  action='ir_agenda_seleccionada.do' method='POST' >"
                    + "<input type='text' name='agenda' style='display:none;' value='"+a.getAgendaPK().getId()+"' required/>"
                    + "<button type='submit'name='selccionada'><i class='fas fa-book'></i><p>"+a.getNombre()+"</p></button>"
                    + "</form>"
                    + "</li>"; 
                }
            }
        }
        return agendas;
    }
    
}
