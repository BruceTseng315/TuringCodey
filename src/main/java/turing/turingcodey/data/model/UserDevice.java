package turing.turingcodey.data.model;

import java.util.Date;

public class UserDevice {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_device.id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_device.user_id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_device.device_serial_number
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    private String deviceSerialNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_device.is_deleted
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    private Byte isDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_device.create_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_device.update_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    private Date updateAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_device.id
     *
     * @return the value of user_device.id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_device.id
     *
     * @param id the value for user_device.id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_device.user_id
     *
     * @return the value of user_device.user_id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_device.user_id
     *
     * @param userId the value for user_device.user_id
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_device.device_serial_number
     *
     * @return the value of user_device.device_serial_number
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public String getDeviceSerialNumber() {
        return deviceSerialNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_device.device_serial_number
     *
     * @param deviceSerialNumber the value for user_device.device_serial_number
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public void setDeviceSerialNumber(String deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber == null ? null : deviceSerialNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_device.is_deleted
     *
     * @return the value of user_device.is_deleted
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_device.is_deleted
     *
     * @param isDeleted the value for user_device.is_deleted
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_device.create_at
     *
     * @return the value of user_device.create_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_device.create_at
     *
     * @param createAt the value for user_device.create_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_device.update_at
     *
     * @return the value of user_device.update_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_device.update_at
     *
     * @param updateAt the value for user_device.update_at
     *
     * @mbg.generated Tue Apr 03 10:44:02 CST 2018
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}