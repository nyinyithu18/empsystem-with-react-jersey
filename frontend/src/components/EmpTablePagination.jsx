import React from "react";

const EmpTablePagination = ({ postPerPage, totalCount, currentPage, handlePageChange }) => {
  const totalPages = Math.ceil(totalCount / postPerPage);

  const renderPaginationButtons = () => {
    const buttons = [];
    for (let i = 1; i <= totalPages; i++) {
      buttons.push(
        <li key={i} className="m-1">
          <button
            className={`px-3 py-2 bg-slate-200 hover:bg-slate-500 hover:text-white hover:rounded ${
              currentPage === i ? "bg-slate-500 text-white" : ""
            }`}
            onClick={() => handlePageChange(i)}
          >
            {i}
          </button>
        </li>
      );
    }
    return buttons;
  };

  return (
    <nav className="mt-4">
      <ul className="flex justify-row justify-center">
        {renderPaginationButtons()}
      </ul>
    </nav>
  );
};

export default EmpTablePagination;
