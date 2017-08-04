/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const userModule = {
  // This makes your getters, mutations, and actions accessed by, eg: 'myModule/myModularizedNumber' instead of mounting getters, mutations, and actions to the root namespace.
  namespaced: true,
  state: {
    userUUID: '',
    alias: '',
    email: '',
    detailsFetched: false,
    details: null,
  },
  getters: {
    getUserAlias: state => state.alias,
    getUserEmail: state => state.email,
    isUserDetailsFetched: state => state.detailsFetched,
    getUserUUID: state => state.userUUID,
    getUserDetails: state => state.details,
  },
  mutations: {
    setUserUUID(state, newUserUUID) {
      state.userUUID = newUserUUID
    },
    setUserAlias(state, newUserAlias) {
      state.alias = newUserAlias
    },
    setDetailsFetched(state, newDetailsFetched) {
      state.detailsFetched = newDetailsFetched
    },
    setUserEmail(state, newUserEmail) {
      state.email = newUserEmail
    },
    setUserDetails(state, newDetails) {
      state.details = newDetails
    },
  }
}

export default userModule