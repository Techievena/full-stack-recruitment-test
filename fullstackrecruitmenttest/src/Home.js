import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Form, Container, Row, Col } from 'reactstrap';
import axios from 'axios';

class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedFile: null
    };
    this.captureFile = this.captureFile.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  captureFile = (event) => {
    event.stopPropagation()
    event.preventDefault()
    const file = event.target.files[0]
    let reader = new window.FileReader()
    reader.readAsArrayBuffer(file)
    reader.onloadend = () => this.setState({selectedFile: file})
  };

  onSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData()
    data.append('file', this.state.selectedFile, this.state.selectedFile.name)
    axios
      .post('/api/uploadFile', data, {
        onUploadProgress: ProgressEvent => {
          console.log(ProgressEvent.loaded / ProgressEvent.total*100)
        },
      })
      .then(res => {
        console.log(res.statusText)
      })
      this.props.history.push('/employees');
    };

  render() {
    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <Row className="text-center">
          <Col>
            <Button color="primary" className="px-4" tag={Link} to="/employees">View Table</Button>
          </Col>
          <Col>
            <Form onSubmit={this.onSubmit}>
              <input type = "file" accept = ".csv" onChange = {this.captureFile}/>
              <Button color="primary" className="px-4">Upload</Button>
            </Form>
          </Col>
          </Row>
        </Container>
      </div>
    );
  }
}

export default Home;