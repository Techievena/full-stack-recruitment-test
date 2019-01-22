import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class EmployeeEdit extends Component {

  emptyItem = {
    name: '',
    department: '',
    designation: '',
    salary: '',
    joindate: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const employee = await (await fetch(`/api/employee/${this.props.match.params.id}`)).json();
      this.setState({item: employee});
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/api/employee', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/employees');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Employee' : 'Add Employee'}</h2>;

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Name</Label>
            <Input type="text" name="name" id="name" value={item.name || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Label for="department">Department</Label>
            <Input type="text" name="department" id="department" value={item.department || ''}
                   onChange={this.handleChange} autoComplete="department"/>
          </FormGroup>
          <FormGroup>
            <Label for="city">Designation</Label>
            <Input type="text" name="designation" id="designation" value={item.designation || ''}
                   onChange={this.handleChange} autoComplete="designation"/>
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-6 mb-3">
              <Label for="salary">Salary</Label>
              <Input type="text" name="salary" id="salary" value={item.salary || ''}
                     onChange={this.handleChange} autoComplete="salary"/>
            </FormGroup>
            <FormGroup className="col-md-6 mb-3">
              <Label for="joindate">Joining Date</Label>
              <Input type="text" name="joindate" id="joindate" value={item.joindate || ''}
                     onChange={this.handleChange} autoComplete="joindate"/>
            </FormGroup>
          </div>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/employees">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(EmployeeEdit);