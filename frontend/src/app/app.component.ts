import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RequestDto } from '../models/RequestDto';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ReactiveFormsModule, HttpClientModule],
  providers: [],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{

  form!: FormGroup;
  formData: RequestDto = {
    x: -1,
    y: -1,
    n: -1
  };

  constructor(private formBuilder: FormBuilder, private http: HttpClient) { }

  /* 
    requestType:
    false -> GET
    true  -> POST
  */
  ngOnInit(): void {
    this.form = this.formBuilder.group({
      x: [null, Validators.required],
      y: [null, Validators.required],
      n: [null, Validators.required],
      requestType: [false, Validators.required]
    });
  }

  /* 
    Submit form:
    Send to assigned function depending of request type
  */
  onSubmit() {
    if (this.form.valid) {
      const formData = {...this.form.value};
      if(this.form.value["requestType"])
        this.postRequestData(formData);
      else
        this.getRequestData(formData);
    } else
      alert("Error detected in fields validation, please check the form and try again");
  }

  /* 
    Post request of data
  */
  postRequestData = (data: RequestDto) => {
    this.http.post<any>('http://localhost:8080/demo/calculatetest', data)
      .subscribe(
        data => {
          alert("Your result is:" + data.response );
        },
        error => {
          console.error('Error:', error);
          // Handle errors here
        }
      );
  }

  /* 
    Get request of data
  */
  getRequestData = (data: RequestDto) => {
    const url = `http://localhost:8080/demo/calculatetest?x=${data.x}&y=${data.y}&n=${data.n}`;
    this.http.get<any>(url)
      .subscribe(
        data => {
          alert("Your result is:" + data.response );
        },
        error => {
          console.error('Error:', error);
          // Handle errors here
        }
      );
  }
}
