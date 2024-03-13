import { api } from "../api/ApiResources";

// Employee
export const empDataPost = (empData) =>{
    return api.post("/addEmp", empData);
}

export const searchByEmpId = (emp_id) => {
    return api.get(`/employee/empDetails/${emp_id}`)
}

export const editEmployeeData = (emp_id, data) =>{
    return api.put(`/employee/editEmpData/${emp_id}`, data)
}

export const editEmpDataWithImage = (emp_id, data) =>{
    return api.put(`/employee/editEmpDataWithImage/${emp_id}`, data)
}

// upload excel file
export const uploadExcelFile = (file) =>{
    return api.post('/uploadFile', file);
}
