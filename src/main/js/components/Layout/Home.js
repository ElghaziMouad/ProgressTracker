import { faEdit, faPlay, faStop, faTrash, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import axios from 'axios';
import moment from 'moment';
import React, { Component } from 'react'
import Select from "react-select";
import "../../styles/home.scss"
import ProjectModal from '../utils/ProjectModal';

class Home extends Component {

  constructor() {
    super();
    this.state = {
      projects: [],
      myProject: null,
      timeHistory: [],
      run :false,
      plusDuration: 0,
      showProjectModal: false,
    }
  }

  componentDidMount() {
    axios.get("/api/project/all")
      .then(res => {
        this.setState({projects: res.data})
      })
    setInterval(() => {
      if(this.state.run) {
        console.log({value1: moment().valueOf(), value2: moment(this.state.timeHistory[0].startDate).valueOf()})
        this.setState({plusDuration: moment().valueOf() - moment(this.state.timeHistory[0].startDate).valueOf()})
      } else {
        if(this.state.plusDuration !== 0) {
          this.setState({plusDuration: 0})
        }
      }
    }, 499)
  } 

  renderDuration(msTime) {
    let str = "";
    let hours = msTime / (1000*60*60)
    let min =  (msTime % (1000*60*60))/(1000*60)
    let sec = ((msTime % (1000*60*60))%(1000*60))/1000
    if(hours>=1) {
      str += Math.floor(hours) + " h "
    }
    if(min >=1) {
      str += Math.floor(min) + " min "
    }
    if(sec>=1) {
      str += Math.floor(sec) + " s "
    }
    return str;
  }

  renderTotalTime = () => {
    let total = 0;
    this.state.timeHistory.map(elem => {
      if(elem.duration) {
        total += elem.duration;
      }
      
    })
    if(this.state.run) {
      return this.renderDuration(total+this.state.plusDuration);
    }
    return this.renderDuration(total);
  }
  
  render() {
    let projectOptions = this.state.projects.map((project) => {return {label: project.name, value: project}})
    return <div className="container p-4 ">
      <div className ="d-flex p-4"> 
        <span className="pt-1">Select project : </span>
        <div className="flex-grow-1 mx-4">
          <Select id={"project"} name={"project"} classNamePrefix="rs"
            isClearable={true}
            value={projectOptions.find((elem) => this.state.myProject && this.state.myProject.id === elem.value.id )}
            options={projectOptions}
            onChange={(option) => {
              this.setState({myProject: option.value})
              axios.get(`/api/timeHistory/project/${option.value.id}`)
              .then(res => {
                let arr = res.data.sort((a,b) => b.id - a.id)
                if(arr.length > 0) {
                  if(arr[0].endDate) {
                    this.setState({timeHistory: res.data.sort((a,b) => b.id - a.id), run: false})
                  } else {
                    this.setState({timeHistory: res.data.sort((a,b) => b.id - a.id), run: true})
                  }
                } else {
                  this.setState({timeHistory: [], run: false})
                }
                
              })
            }}
          />
        </div>
        {this.state.run 
          ? <button type="button" className="btn btn-danger" onClick={() => {
            axios.post(`/api/timeHistory/project/${this.state.myProject.id}/next`)
              .then((res) => {
                axios.get(`/api/timeHistory/project/${this.state.myProject.id}`)
                  .then(res => {
                    this.setState({timeHistory: res.data.sort((a,b) => b.id - a.id), run: false})
                  })
              })
            
          }}>
          <FontAwesomeIcon icon={faStop} />
        </button>
          : <button type="button" className="btn btn-success" onClick={() => {
            axios.post(`/api/timeHistory/project/${this.state.myProject.id}/next`)
              .then((res) => {
                axios.get(`/api/timeHistory/project/${this.state.myProject.id}`)
                  .then(res => {
                    this.setState({timeHistory: res.data.sort((a,b) => b.id - a.id), run: true})
                  })
              })
          }}>
          <FontAwesomeIcon icon={faPlay} />
        </button>}
        
        <button 
          type="button" 
          className="btn btn-primary ml-2"
          onClick={() => this.setState({showProjectModal: true})}
        >
          <FontAwesomeIcon icon={faEdit} />
        </button>
        
      </div>
      <div className="row">
        <div className="col-7 table-responsive content-history">
          <table className="table">
            <thead>
              <tr>
                <th scope="col">start</th>
                <th scope="col">end</th>
                <th scope="col">duration</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {this.state.timeHistory.map((elem, ind) => {
                return (
                  <tr key={`table-${ind}`}>
                    <td>{elem.startDate}</td>
                    <td>{elem.endDate ? elem.endDate : "..."}</td>
                    {elem.endDate 
                      ? <td>{elem.duration && this.renderDuration(elem.duration)}</td> 
                      : <td style={{color: 'green'}}>{this.renderDuration(this.state.plusDuration)}</td>
                    }
                    <td>
                      {elem.endDate && 
                      <div onClick={() => {
                        if(window.confirm('Êtes-vous sûr?')) {
                          axios.delete(`/api/timeHistory/delete/${elem.id}`)
                          .then(() => {
                            this.setState({timeHistory:this.state.timeHistory.filter(e=>e.id !== elem.id)})
                          })
                        }
                      }}>
                        <FontAwesomeIcon
                          icon={faTrashAlt}
                          color="red"
                        />
                      </div>}
                    </td>
                  </tr>
                )
              })}
              
            </tbody>
          </table>
        </div>
        <div className="col-5 content-history">
          <h1>TOTAL: {this.renderTotalTime()}</h1>

        </div>
      </div>
      <ProjectModal
        show={this.state.showProjectModal}
        onHide={() => {
          axios.get("/api/project/all")
            .then(res => {
              this.setState({projects: res.data})
            })
          this.setState({showProjectModal: false})
        }}
      />
    </div>;
  }
}



export default Home;
