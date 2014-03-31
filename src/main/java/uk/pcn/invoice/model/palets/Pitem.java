package uk.pcn.invoice.model.palets;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pitems database table.
 * 
 */
@Entity
@Table(name="pitems")
public class Pitem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
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
	@ManyToOne
	@JoinColumn(name="palets_id")
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

}