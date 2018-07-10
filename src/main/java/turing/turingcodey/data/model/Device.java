package turing.turingcodey.data.model;

import java.util.Date;

public class Device {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column devices.id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column devices.device_serial_number
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    private String deviceSerialNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column devices.ali_device_id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    private Long aliDeviceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column devices.create_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column devices.update_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    private Date updateAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column devices.id
     *
     * @return the value of devices.id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column devices.id
     *
     * @param id the value for devices.id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column devices.device_serial_number
     *
     * @return the value of devices.device_serial_number
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public String getDeviceSerialNumber() {
        return deviceSerialNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column devices.device_serial_number
     *
     * @param deviceSerialNumber the value for devices.device_serial_number
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public void setDeviceSerialNumber(String deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber == null ? null : deviceSerialNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column devices.ali_device_id
     *
     * @return the value of devices.ali_device_id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public Long getAliDeviceId() {
        return aliDeviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column devices.ali_device_id
     *
     * @param aliDeviceId the value for devices.ali_device_id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public void setAliDeviceId(Long aliDeviceId) {
        this.aliDeviceId = aliDeviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column devices.create_at
     *
     * @return the value of devices.create_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column devices.create_at
     *
     * @param createAt the value for devices.create_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column devices.update_at
     *
     * @return the value of devices.update_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column devices.update_at
     *
     * @param updateAt the value for devices.update_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}