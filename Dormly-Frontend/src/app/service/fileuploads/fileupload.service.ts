import { Injectable } from '@angular/core';
import { Form } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FileuploadService {
  

  constructor() { }

  uploadFile(event: Event):FormData{
    const input = event.target as HTMLInputElement
    //handle the possibility of it being null
    if(input.files!==null && input.files.length>0){
      const file:File = input.files[0]

      //backend is expecting a multipart form data object 
      const formdata = new FormData()
      formdata.append("file", file)
      return formdata
    }else{
      throw new Error("there was no file found")
    }

    }
    

    
  }

