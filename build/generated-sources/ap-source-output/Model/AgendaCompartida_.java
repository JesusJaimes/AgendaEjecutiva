package Model;

import Model.Agenda;
import Model.AgendaCompartidaPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-27T09:03:37")
@StaticMetamodel(AgendaCompartida.class)
public class AgendaCompartida_ { 

    public static volatile SingularAttribute<AgendaCompartida, AgendaCompartidaPK> agendaCompartidaPK;
    public static volatile SingularAttribute<AgendaCompartida, Integer> permisos;
    public static volatile SingularAttribute<AgendaCompartida, Agenda> agenda;

}