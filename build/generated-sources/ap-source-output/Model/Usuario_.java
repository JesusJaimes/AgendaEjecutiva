package Model;

import Model.Agenda;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-27T09:03:37")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> password;
    public static volatile ListAttribute<Usuario, Agenda> agendaList;
    public static volatile SingularAttribute<Usuario, Date> fecharegistro;
    public static volatile SingularAttribute<Usuario, String> cargo;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile SingularAttribute<Usuario, String> email;

}