import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DataService } from '../service/data.service';

@Component({
  selector: 'app-meal',
  templateUrl: './meal.component.html',
  styleUrls: ['./meal.component.scss']
})
export class MealComponent implements OnInit {
  //form group using reative forms
  restMeal: FormGroup;
  previewImage: string | ArrayBuffer;
  selectedImage: File;
  constructor(private router: Router, private fb: FormBuilder,private dataService: DataService) { }

  ngOnInit(): void {

    //reative form validations 
    this.restMeal = this.fb.group({
      price: ['', [Validators.required, Validators.maxLength(50)]],//any time more than one validation is written be sure to put it in an array unless it will throw [ validator to return Promise or Observable]
      timeTaken: ['', [Validators.required, Validators.maxLength(50)]],
      description: ['', [Validators.required]],
      gender: ['', Validators.required],
      mealImage: ['', Validators.required],
    })

  }

  //image preview
  onImagePicked(event: Event) {
    const file = <File>(event.target as HTMLInputElement).files[0];
    this.restMeal.patchValue({ mealImage: file });
    this.restMeal.get('mealImage').updateValueAndValidity();

    const reader = new FileReader();
    reader.onload = () => {
      this.previewImage = reader.result;
    };
    reader.readAsDataURL(file);
    this.selectedImage = file;
  }

  saveMeal(){
    this.dataService.save(
      this.restMeal.value.description,
      this.restMeal.value.name,
      this.selectedImage,
      this.restMeal.value.price,
      this.restMeal.value.timeTaken).subscribe(
        res => {
          this.router.navigate(['/restaurant']);
        },
        err=> console.log(err)
      )
    
  }



}
