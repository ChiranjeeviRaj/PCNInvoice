package uk.pcn.invoice.model.palets;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the pitems database table.
 * 
 */
//@SequenceGenerator(name = "id", sequenceName = "some_set_seq", initialValue = 1, allocationSize = 1)
@Entity
@Table(name="pitems")
public class Pitem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "pitems_id")
	private int pitemsId;

	private int con;

	@Temporal(TemporalType.DATE)
	private Date dateReq;

	private String delPoc;

	private String delTown;

	@Temporal(TemporalType.DATE)
	private Date input;

	private int plts;

	@Temporal(TemporalType.DATE)
	private Date podDate;

	private String podName;

	private String podTime;

	private String ref;

	private String service;

	private String timeReq;

	//bi-directional many-to-one association to Palet
/*	@ManyToOne
	@JoinColumn(name="palet_id")
	*/
    @ManyToOne
    @JoinColumn(name="palet_id",
                insertable=true, updatable=true,
                nullable=true)
	private Palet palet;

	public Pitem() {
	}

	public int getCon() {
		return this.con;
	}

	public void setCon(int con) {
		this.con = con;
	}

	public Date getDateReq() {
		return this.dateReq;
	}

	public void setDateReq(Date dateReq) {
		this.dateReq = dateReq;
	}

	public String getDelPoc() {
		return this.delPoc;
	}

	public void setDelPoc(String delPoc) {
		this.delPoc = delPoc;
	}

	public String getDelTown() {
		return this.delTown;
	}

	public void setDelTown(String delTown) {
		this.delTown = delTown;
	}

	public Date getInput() {
		return this.input;
	}

	public void setInput(Date input) {
		this.input = input;
	}

	public int getPlts() {
		return this.plts;
	}

	public void setPlts(int plts) {
		this.plts = plts;
	}

	public Date getPodDate() {
		return this.podDate;
	}

	public void setPodDate(Date podDate) {
		this.podDate = podDate;
	}

	public String getPodName() {
		return this.podName;
	}

	public void setPodName(String podName) {
		this.podName = podName;
	}

	public String getPodTime() {
		return this.podTime;
	}

	public void setPodTime(String podTime) {
		this.podTime = podTime;
	}

	public String getRef() {
		return this.ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTimeReq() {
		return this.timeReq;
	}

	public void setTimeReq(String timeReq) {
		this.timeReq = timeReq;
	}

	public Palet getPalet() {
		return this.palet;
	}

	public void setPalet(Palet palet) {
		this.palet = palet;
	}

	public int getPitemsId() {
		return pitemsId;
	}

	public void setPitemsId(int pitemsId) {
		this.pitemsId = pitemsId;
	}

}