package uk.pcn.invoice.model.palets;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.List;


/**
 * The persistent class for the palets database table.
 * 
 */
@Entity
@Table(name="palets")
public class Palet implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name="table-hilo-generator", strategy="org.hibernate.id.TableHiLoGenerator",
			  parameters={@Parameter(value="hibernate_id_generation", name="table")})

			 //I use the generator configured above
	@GeneratedValue(generator="table-hilo-generator")
	@Id
	private int id;

	//bi-directional many-to-one association to Pitem
	@OneToMany(mappedBy="palet")
	private List<Pitem> pitems;

	public Palet() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Pitem> getPitems() {
		return this.pitems;
	}

	public void setPitems(List<Pitem> pitems) {
		this.pitems = pitems;
	}

	public Pitem addPitem(Pitem pitem) {
		getPitems().add(pitem);
		pitem.setPalet(this);

		return pitem;
	}

	public Pitem removePitem(Pitem pitem) {
		getPitems().remove(pitem);
		pitem.setPalet(null);

		return pitem;
	}

}