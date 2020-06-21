package Model;

import Model.Agenda;
import Model.CitaPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-19T21:26:27")
@StaticMetamodel(Cita.class)
public class Cita_ { 

    public static volatile SingularAttribute<Cita, String> descripcion;
    public static volatile SingularAttribute<Cita, Date> fecha;
    public static volatile SingularAttribute<Cita, CitaPK> citaPK;
    public static volatile SingularAttribute<Cita, Date> hora;
    public static volatile SingularAttribute<Cita, String> asunto;
    public static volatile SingularAttribute<Cita, Agenda> agenda;
    public static volatile SingularAttribute<Cita, Boolean> completada;

}