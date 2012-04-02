package de.paluch.burndown.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * The Sprint-Effort for a Sprint-Day. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 19.03.2012 <br>
 * <br>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SprintEffort {

    private Double burned = 0d;
    private Date date;
    private Double unplanned = 0d;

    /**
	 *
	 */
    public SprintEffort() {

        super();
    }

    /**
     * @param date
     * @param burned
     * @param unplanned
     */
    public SprintEffort(Date date, double burned, double unplanned) {

        super();
        this.date = date;
        this.burned = burned;
        this.unplanned = unplanned;
    }

    /**
     * @param burned
     * @param unplanned
     */
    public SprintEffort(double burned, double unplanned) {

        super();
        this.burned = burned;
        this.unplanned = unplanned;
    }

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
        SprintEffort other = (SprintEffort) obj;
        if (burned == null) {
            if (other.burned != null) {
                return false;
            }
        } else if (!burned.equals(other.burned)) {
            return false;
        }
        if (date == null) {
            if (other.date != null) {
                return false;
            }
        } else if (!date.equals(other.date)) {
            return false;
        }
        if (unplanned == null) {
            if (other.unplanned != null) {
                return false;
            }
        } else if (!unplanned.equals(other.unplanned)) {
            return false;
        }
        return true;
    }

    /**
     * @return the burned
     */
    public Double getBurned() {

        return burned;
    }

    /**
     * @return the date
     */
    public Date getDate() {

        return date;
    }

    /**
     * @return the unplanned
     */
    public Double getUnplanned() {

        return unplanned;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((burned == null) ? 0 : burned.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((unplanned == null) ? 0 : unplanned.hashCode());
        return result;
    }

    /**
     * @param burned
     *            the burned to set
     */
    public void setBurned(Double burned) {

        this.burned = burned;
        if (this.burned == null) {
            this.burned = 0d;
        }
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(Date date) {

        this.date = date;
    }

    /**
     * @param unplanned
     *            the unplanned to set
     */
    public void setUnplanned(Double unplanned) {

        this.unplanned = unplanned;
        if (this.unplanned == null) {
            this.unplanned = 0d;
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return getClass().getSimpleName() + " [date=" + date + ", burned=" + burned + ", unplanned=" + unplanned + "]";
    }

}
