import axios from 'axios';
import React, { Component } from 'react'
import { Modal } from 'react-bootstrap';

class ProjectModal extends Component {

  constructor() {
    super();
    this.state= {
      newProject: "",
      projects: []
    }
  }

  componentDidMount() {
    axios.get("/api/project/all")
      .then(res => {
        this.setState({projects: res.data})
      })
  }

  render() {
    return (
      <Modal
        show={this.props.show}
        onHide={this.props.onHide}
        // className=""
        size="lg"
        centered
      >
        <Modal.Header>
          Projects
        </Modal.Header>
        <Modal.Body style={{padding: '30px 20px'}}>
          <div>
            <div className="d-flex">
              <input 
                placeholder="Enter your new project" className={`form-control flex-grow-1 input-sm`}
                type="text"
                value={this.state.newProject}
                onChange={event => this.setState({newProject: event.target.value})}
              />
              <button type="button" 
                className="btn btn-danger ml-2"
                onClick={() => {
                  axios.post("/api/project/save", {name:this.state.newProject})
                    .then((res) => this.setState({projects: [...this.state.projects, res.data]}))
                }}>Save</button>
            </div>
            <div className="col-7 table-responsive content-history">
              <table className="table">
                <thead>
                  <tr>
                    <th scope="col">Projects</th>
                  </tr>
                </thead>
                <tbody>
                  {this.state.projects.map(elem => {
                    return (
                      <tr>
                        <td>{elem.name}</td>
                      </tr>
                    )

                  })}
                </tbody>
              </table>
            </div>
          </div>
        </Modal.Body>
      </Modal>
    )
  }
}

export default ProjectModal;
