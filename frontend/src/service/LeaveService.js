import { api } from "../api/ApiResources";

export const leaveDataPost = (leaveDate) =>{
    return api.post("/leave/addLeave", leaveDate);
}

export const leaveEditData = (leave_id, data) => {
    return api.put(`leave/editLeaveData/${leave_id}`, data)
}

export const leaveList = () =>{
    return api.get('/leave/leaveList')
}

export const searchByLeaveId = (leave_id) => {
    return api.get(`/searchByLeaveId?leave_id=${leave_id}`)
}


