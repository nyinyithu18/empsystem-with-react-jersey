import { Button, Label, Select, TextInput } from "flowbite-react";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { api } from "../api/ApiResources";
import EmpTableData from "./EmpTableData";
import EmpTablePagination from "./EmpTablePagination";
import ImportExcel from "./ImportExcel";

const EmpList = () => {
  const [empData, setEmpData] = useState([]);

  const [currentPage, setCurrentPage] = useState(1);
  const [postPerPage, setPostPerPage] = useState(5);
  const [totalCount, setTotalCount] = useState(0);
  const [search, setSearch] = useState("");

  // Fetch for Pagination
  const FetchPaginate = async () => {
    try {
      let res;
      if (search) {
        res = await api.get(
          `/empLeave/findWithPager?page=${
            currentPage - 1
          }&limit=${postPerPage}&search=${search}`
        );
        console.log(res.data);
        setEmpData(res.data.emp);
        setTotalCount(res.data.totalCount);
      } else {
        res = await api.get("/employee/empList");
        setEmpData(res.data);
        setTotalCount(res.data.length);
      }
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  // Export With excel file
  const exportExcel = async () => {
    try {
      const res = await api.get(`/empLeave/export?search=${search}`, {
        responseType: "blob",
      });

      if (res.status >= 200 && res.status < 300) {
        const blob = new Blob([res.data], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = "employee_data.xlsx";
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
      } else {
        console.error("Failed to export data: ", res.statusText);
        alert("Failed to export data!");
      }
    } catch (error) {
      console.error("Error exporting data: ", error);
    }
  };

  const handleExport = () => {
    exportExcel();
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  useEffect(() => {
    FetchPaginate();
  }, [currentPage, postPerPage, search.length]);

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
              placeholder="Search..... "
              onChange={(e) => setSearch(e.target.value)}
            />
          </div>
          <div className="flex">
            <ImportExcel fetchEmpData={FetchPaginate} />
          </div>
          <div>
            <Button
              onClick={handleExport}
              className="bg-blue-500"
              type="button"
            >
              Export
            </Button>
          </div>
          <div>
            <Link to="/">
              <Button className="btn bg-blue-500 w-20">New</Button>
            </Link>
          </div>
        </div>
      </div>
      {search ? (
        <EmpTableData empData={empData} />
      ) : (
        <EmpTableData
          empData={empData.slice(
            (currentPage - 1) * postPerPage,
            currentPage * postPerPage
          )}
        />
      )}
      <div className="flex justify-center">
        <div className="w-20 me-8 mt-5">
          <Select
            value={postPerPage}
            onChange={(e) => setPostPerPage(e.target.value)}
          >
            <option value={5}>5</option>
            <option value={10}>10</option>
            <option value={20}>20</option>
            <option value={50}>50</option>
            <option value={100}>100</option>
          </Select>
        </div>
        <EmpTablePagination
          postPerPage={postPerPage}
          totalCount={totalCount}
          currentPage={currentPage}
          handlePageChange={handlePageChange}
        />
        <div className="mt-6 ms-8">
          <p>
            TotalCount : <span>{totalCount}</span>
          </p>
        </div>
      </div>
    </div>
  );
};

export default EmpList;
