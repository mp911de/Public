package de.paluch.burndown.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Sprint. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 19.03.2012 <br>
 * <br>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sprint")
public class Sprint {

    private int days;

    private List<SprintEffort> effort = new ArrayList<SprintEffort>();
    @XmlAttribute
    private String id = "";
    private double planned = 0;

    private Date startDate;

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Sprint other = (Sprint) obj;
        if (days != other.days) {
            return false;
        }
        if (effort == null) {
            if (other.effort != null) {
                return false;
            }
        } else if (!effort.equals(other.effort)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (Double.doubleToLongBits(planned) != Double.doubleToLongBits(other.planned)) {
            return false;
        }
        if (startDate == null) {
            if (other.startDate != null) {
                return false;
            }
        } else if (!startDate.equals(other.startDate)) {
            return false;
        }
        return true;
    }

    /**
     * @return the days
     */
    public int getDays() {

        return days;
    }

    /**
     * @return the effort
     */
    public List<SprintEffort> getEffort() {

        return effort;
    }

    /**
     * @return the id
     */
    public String getId() {

        return id;
    }

    /**
     * @return the planned
     */
    public double getPlanned() {

        return planned;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {

        return startDate;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + days;
        result = prime * result + ((effort == null) ? 0 : effort.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        long temp;
        temp = Double.doubleToLongBits(planned);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        return result;
    }

    /**
     * @param days
     *            the days to set
     */
    public void setDays(int days) {

        this.days = days;
    }

    /**
     * @param effort
     *            the effort to set
     */
    public void setEffort(List<SprintEffort> effort) {

        this.effort = effort;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * @param planned
     *            the planned to set
     */
    public void setPlanned(double planned) {

        this.planned = planned;
    }

    /**
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(Date startDate) {

        this.startDate = startDate;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return getClass().getSimpleName() + " [id=" + id + ", days=" + days + ", startDate=" + startDate + ", planned="
                + planned + ", effort=" + effort + "]";
    }

}
