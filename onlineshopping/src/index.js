import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import allreducer from "./redux/Reducers";
import {createStore} from "redux";
import {Provider} from "react-redux";

function saveToLocalStorage(state){

    try{
        const serializedState= JSON.stringify(state);
        localStorage.setItem('state',serializedState)
    }catch(e){
        console.log(e)
    }

}



function loadFromLocalStorage(){

    try{
        const serializedState= localStorage.getItem('state');
        if(serializedState===null) return undefined;
        return JSON.parse(serializedState)
    }catch (e) {
        console.log(e)
        return undefined;
    }
}


const persistedReducer= loadFromLocalStorage();

const store= createStore(allreducer,persistedReducer,window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());

store.subscribe(()=>{saveToLocalStorage(store.getState())
})


ReactDOM.render(
  <React.StrictMode>
      <Provider store={store}>
    <App />
      </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);


serviceWorker.unregister();