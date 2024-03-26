import { Table, Dropdown } from "flowbite-react";
import React from "react";
import { useNavigate } from "react-router";
import { editEmployeeData } from "../service/EmpService";

const EmpTableData = ({ empData }) => {
  const navigate = useNavigate();

  const fetchEditData = async (emp_id, emp) => {
    await editEmployeeData(emp_id, emp);
    window.location.reload();
  };

  // Check delete Employee Data for soft delete
  const handleCheckDelete = (emp_id) => {
    if (window.confirm("Are you sure?")) {
      const editempdata = empData.map((emp) => {
        if (emp.emp_id == emp_id) {
          return { ...emp, checkdelete: 0 };
        }
        return emp;
      });

      editempdata.map((emp) => {
        if (emp.emp_id == emp_id) {
          fetchEditData(emp_id, emp);
        }
      });
    }
  };

  return (
    <div className="overflow-x-auto mt-6 mx-5">
      <Table hoverable striped>
        <Table.Head className="bg-slate-500">
          <Table.HeadCell>ID</Table.HeadCell>
          <Table.HeadCell>Name</Table.HeadCell>
          <Table.HeadCell>NRC</Table.HeadCell>
          <Table.HeadCell>Phone</Table.HeadCell>
          <Table.HeadCell>Email</Table.HeadCell>
          <Table.HeadCell>Date of Birth</Table.HeadCell>
          <Table.HeadCell>Rank</Table.HeadCell>
          <Table.HeadCell>Department</Table.HeadCell>
          <Table.HeadCell>Address</Table.HeadCell>
          <Table.HeadCell>Action</Table.HeadCell>
        </Table.Head>
        <Table.Body className="divide-y">
          {empData.length > 0 && empData != null? (
            empData.map((emp, index) => {
             // if (emp.checkdelete == 0) {
                return (
                  <Table.Row
                    key={index}
                    className=""
                  >                   
                    <Table.Cell className="whitespace-nowrap font-medium text-gray-900 dark:text-white">
                      {emp.emp_id}
                    </Table.Cell>
                    <Table.Cell>{emp.emp_name}</Table.Cell>
                    <Table.Cell>{emp.nrc}</Table.Cell>
                    <Table.Cell>{emp.phone}</Table.Cell>
                    <Table.Cell>{emp.email}</Table.Cell>
                    <Table.Cell>{emp.dob}</Table.Cell>
                    <Table.Cell>{emp.rank}</Table.Cell>
                    <Table.Cell>{emp.dep}</Table.Cell>
                    <Table.Cell>{emp.address}</Table.Cell>
                    <Table.Cell>
                      <Dropdown label="Action" inline>
                        <Dropdown.Item
                          onClick={() => navigate(`/editEmpData/${emp.emp_id}`)}
                        >
                          Edit
                        </Dropdown.Item>
                        <Dropdown.Item
                          onClick={() => handleCheckDelete(emp.emp_id)}
                        >
                          Delete
                        </Dropdown.Item>
                      </Dropdown>
                    </Table.Cell>
                  </Table.Row>
                );
             // }
            })
          ) : (
            <Table.Row className="bg-white dark:border-gray-700 dark:bg-gray-800">
              <Table.Cell className="whitespace-nowrap font-medium text-gray-900 dark:text-white">
                No Data
              </Table.Cell>
              <Table.Cell>No Data</Table.Cell>
              <Table.Cell>No Data</Table.Cell>
              <Table.Cell>NO Data</Table.Cell>
              <Table.Cell>No Data</Table.Cell>
              <Table.Cell>No Data</Table.Cell>
              <Table.Cell>No Data</Table.Cell>
              <Table.Cell>No Data</Table.Cell>
              <Table.Cell>No Data</Table.Cell>
            </Table.Row>
          )}
        </Table.Body>
      </Table>
    </div>
  );
};

export default EmpTableData;
