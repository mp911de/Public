package de.paluch.test.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestModel {

    private int result;

    /**
     *
     */
    public TestModel() {
        super();
    }

    /**
     * @param result
     */
    public TestModel(int result) {
        super();
        this.result = result;
    }

    /**
     * @return the result
     */
    public int getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(int result) {
        this.result = result;
    }

}
