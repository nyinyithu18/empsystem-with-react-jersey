import { Table, Dropdown } from "flowbite-react";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router";

const EmpTableData = ({ empData, handleCheckDelete }) => {
  const navigate = useNavigate();
  const [displayData, setDisplyData] = useState([])

  const checkData = () => {
    const filteredData = empData.filter(emp => emp.checkdelete !== 1);
    setDisplyData(filteredData)
  }

  useEffect(()=>{
    checkData();
  }, [displayData.length])

  return (
    <div className="overflow-x-auto mt-6 mx-5">
      <Table hoverable>
        <Table.Head>
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
          {empData.length > 0 ? (
            empData.map((emp, index) => {
             // if (emp.checkdelete == 0) {
                return (
                  <Table.Row
                    key={index}
                    className="bg-white dark:border-gray-700 dark:bg-gray-800"
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
