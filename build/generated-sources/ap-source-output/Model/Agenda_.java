package Model;

import Model.AgendaPK;
import Model.Cita;
import Model.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-25T02:50:10")
@StaticMetamodel(Agenda.class)
public class Agenda_ { 

    public static volatile SingularAttribute<Agenda, String> descripcion;
    public static volatile SingularAttribute<Agenda, Date> fecha;
    public static volatile SingularAttribute<Agenda, Usuario> usuario1;
    public static volatile SingularAttribute<Agenda, AgendaPK> agendaPK;
    public static volatile ListAttribute<Agenda, Cita> citaList;

}