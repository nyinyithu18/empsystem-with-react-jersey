import { Button, Label, Select, TextInput } from "flowbite-react";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { api } from "../api/ApiResources";
import ExcelExport from "./ExcelExport";
import EmpTableData from "./EmpTableData";
import EmpTablePagination from "./EmpTablePagination";
import ImportExcel from "./ImportExcel";

const EmpList = () => {
  const [empData, setEmpData] = useState([]);
  const [filteredData, setFilteredData] = useState([]);
  const [empLeaveData, setEmpLeaveData] = useState([]);

  const fileName = "Emp Datas";


  const [currentPage, setCurrentPage] = useState(1);
  const [postPerPage, setPostPerPage] = useState(5);
  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  // Fetch Emp Data
  const FetchEmpData = async () => {
    const res = await api.get("/employee/empList");
    setEmpData(res.data);
    setFilteredData(res.data);
  };

  // Fetch Emp and Leave Data
  const FetchEmpLeaveData = async () => {
    const res = await api.get("/empLeave/empLeaveList");
    setEmpLeaveData(res.data);
  };

  useEffect(() => {
    FetchEmpData();
    FetchEmpLeaveData();
  }, []);

  return (
    <div className="">
      <div className="flex justify-between mx-6">
        <div></div>
        <div className="flex flex-rows gap-4">
          <div className="flex">
            <Label
              htmlFor="search"
              value="Search"
              className="mt-2 me-1 text-md"
            />
            <TextInput
              id="search"
              type="text"
              sizing="md"
              placeholder="ID... / Name... "
              onChange={(e) =>
                setEmpData(
                  filteredData.filter(
                    (f) =>
                      f.emp_id == e.target.value ||
                      f.emp_name.toLowerCase().includes(e.target.value)
                  )
                )
              }
            />
          </div>
          <div className="flex">
            <ImportExcel
              fetchEmpData={FetchEmpData}
              fetchEmpLeaveData={FetchEmpLeaveData}
            />
          </div>
          <div>
            <ExcelExport excelData={empLeaveData} fileName={fileName} />
          </div>
          <div>
            <Link to="/">
              <Button className="btn bg-blue-500 w-20">New</Button>
            </Link>
          </div>
        </div>
      </div>
      <EmpTableData
        empData={empData.slice(
          (currentPage - 1) * postPerPage,
          currentPage * postPerPage
        )}
      />
      <div className="flex justify-between my-4">
        <div className="w-20 ms-7">
          <Select value={postPerPage} onChange={(e) => setPostPerPage(e.target.value)} required>
          <option value={5}>5</option>
            <option value={10}>10</option>
            <option value={20}>20</option>
            <option value={50}>50</option>
            <option value={100}>100</option>
          </Select>
        </div>
        <EmpTablePagination
          postPerPage={postPerPage}
          totalPost={empData.length}
          paginate={paginate}
        />
        <div></div>
      </div>
    </div>
  );
};

export default EmpList;
