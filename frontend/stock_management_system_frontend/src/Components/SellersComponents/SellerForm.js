import React, { useEffect } from "react";
import { Form, Input, Button, Radio, message } from "antd";
import { addSeller, updateSeller } from "../../Services/SellerService";
import { CONSTANTS } from "../../Common files/Constants";

const SellerForm = ({
  editingSeller,
  onUpdateSeller,
  onFormSubmit,
  onClose,
}) => {
  const [form] = Form.useForm();

  useEffect(() => {
    if (editingSeller) {
      form.setFieldsValue(editingSeller);
    } else {
      form.resetFields();
    }
  }, [editingSeller, form]);

  const handleSubmit = async (values) => {
    try {
      if (editingSeller) {
        const updatedSeller = { ...values, sellerId: editingSeller.sellerId };
        const response = await updateSeller(
          editingSeller.sellerId,
          updatedSeller
        );
        console.log("response of update :", response);
        if (response.status === CONSTANTS.HttpStatusString.OK) {
          message.success("Seller updated successfully!");
          onUpdateSeller(updatedSeller);
        }
      } else {
        const response = await addSeller(values);
        if (response.status === CONSTANTS.HttpStatus.CREATED) {
          message.success("Seller added successfully!");
          onFormSubmit(response.data);
        }
      }
      onClose();
    } catch (error) {
      if (error.response && error.response.status === 409) {
        message.warning("Seller already exists. Please try again.");
      } else {
        message.error("Something went wrong. Please try again.");
      }
    }
  };

  return (
    <Form
      form={form}
      layout="vertical"
      onFinish={handleSubmit}
      initialValues={editingSeller || {}}
    >
      <Form.Item
        label="Seller Name"
        name="sellerName"
        rules={[{ required: true, message: "Please enter the seller name!" }]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        label="Email"
        name="email"
        rules={[
          { required: true, message: "Please enter the email!" },
          { type: "email", message: "Please enter a valid email address!" },
        ]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        label="Contact"
        name="contact"
        rules={[
          { required: true, message: "Please enter the contact number!" },
        ]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        label="Address"
        name="address"
        rules={[{ required: true, message: "Please enter the address!" }]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        label="Status"
        name="status"
        rules={[{ required: true, message: "Please select the status!" }]}
      >
        <Radio.Group>
          <Radio value="Active">Active</Radio>
          <Radio value="InActive">InActive</Radio>
        </Radio.Group>
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit" block>
          {editingSeller ? "Update Seller" : "Add Seller"}
        </Button>
      </Form.Item>
    </Form>
  );
};

export default SellerForm;
