import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FileuploadService {
  

  constructor() { }

  uploadFile(event: Event):File{
    const input = event.target as HTMLInputElement
    //handle the possibility of it being null
    if(input.files!==null && input.files.length>0){
      const file:File = input.files[0]
      return file
    }else{
      throw new Error("there was no file found")
    }

    }
    

    
  }

