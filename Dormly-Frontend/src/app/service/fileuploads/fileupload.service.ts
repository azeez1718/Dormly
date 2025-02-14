import { Injectable } from '@angular/core';
import { Form } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FileuploadService {
  
  

  constructor() { }

  upload(event:Event):FormData{
    let input = event.target as HTMLInputElement
    if(input.files!==null && input.files.length==1){
      const file:File = input.files[0]
      const formdata:FormData= new FormData()
      formdata.append('file', file)
     return formdata
    }
    else if(input.files && input.files.length>1){
      throw Error("please select more than one image")
    }
    else{
       //if the user hasnt selected any files, we return an empty formdata object, in which we will later append just the json
      //if it returns a form data it means it is being used by the update listing component
      return new FormData()    
    }
  }  
    

    
  }

