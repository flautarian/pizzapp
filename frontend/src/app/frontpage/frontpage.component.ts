import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { PizzaDto } from 'models/PizzaDto';

import { Utils } from 'utils/utils'; // Import the utility class
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { CurrencyPipe } from '@angular/common';
import { environment } from 'environments/environment';
import { StockWebSocketService } from '@services/StockWebSocketService';
import { IngredientDto } from 'models/IngerdientDto';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-frontpage',
    templateUrl: './frontpage.component.html',
    styleUrls: ['./frontpage.component.css']
})
export class FrontpageComponent implements OnInit {
    form!: FormGroup;

    pizzas: any;
    pizzaSizes: any;
    pizzaIngredients: any;

    private currencyPipe: CurrencyPipe = new CurrencyPipe('en-EN');

    formData: PizzaDto = {
        customerName: '',
        pizzaSize: '',
        deliveryAddress: '',
        status: 'PLACED',
        emailAddress: '',
        pizzaName: '',
        extraIngredients: [],
        price: 0.0
    };

    constructor(
        private fb: FormBuilder,
        private http: HttpClient,
        private stockWebSocketService: StockWebSocketService,
        private toastr: ToastrService) {
    }

    ngOnInit(): void {
        this.form = this.fb.group({
            customerName: [this.formData.customerName, Validators.required],
            pizzaSize: [this.formData.pizzaSize, Validators.required],
            deliveryAddress: [this.formData.deliveryAddress, Validators.required],
            emailAddress: [this.formData.emailAddress, [Validators.required, Validators.email]],
            pizzaName: [this.formData.pizzaName, Validators.required],
            extraIngredients: [this.formData.extraIngredients],
            price: [this.formData.price, Validators.required]
        });

        this.pizzas = Utils.availablePizzas();
        this.pizzaSizes = Utils.availablePizzaSizes();
        this.pizzaIngredients = Utils.availablePizzaIngredients();

        this.stockWebSocketService.getUpdates().subscribe(update => {
            if (Array.isArray(update)) {
                update.forEach(stockUpdate => {
                    const index = this.pizzaIngredients.findIndex((ingredient: IngredientDto) => ingredient.value === stockUpdate.name);
                    if (index !== -1) {
                        this.pizzaIngredients[index].quantity = Number(stockUpdate.quantity);
                    }
                });
            }
            else {
                let ingredientUpdate = update['ingredient'];
                const index = this.pizzaIngredients.findIndex((ingredient: IngredientDto) => ingredient.value === ingredientUpdate.name);
                if (index !== -1) {
                    this.pizzaIngredients[index].quantity = Number(ingredientUpdate.quantity);
                }
            }
        });
    }

    submitForm = () => {
        if (this.form.valid) {
            const formData = { ...this.form.value };
            this.postRequestData(formData);
        } else
            this.toastr.error("Error detected in fields validation, please check the form and try again");
    }

    selectOption = (key: string, value: string, cssSuffix: string) => {
        let element = document.getElementById(value + '-id') as HTMLElement;
        this.form.patchValue({ [key]: value });
        let options = document.querySelectorAll('.' + cssSuffix);
        options.forEach(option => option.classList.remove('select-option-selected'));
        if (element.className.indexOf(cssSuffix) < 0) {
            let parent = element.parentNode as HTMLElement;
            parent?.classList.add('select-option-selected');
        }
        else element.classList.add('select-option-selected');
        /* RECALC PRICE */
        this.recalculatePrice();
    }

    getNeededQuantity(ingredientName: string): number {
        let neededQuantity = 1;
        let pizzaSize = this.form.value["pizzaSize"];
        if (!!pizzaSize)
            neededQuantity = this.pizzaSizes.find((size: { value: string; }) => size.value === pizzaSize).price;
        
        let currentValues = this.form.value["extraIngredients"];
        if (currentValues.includes(ingredientName)) {
            let ingredient = this.pizzaIngredients.find((ingredient: IngredientDto) => ingredient.value === ingredientName) || null;
            if (ingredient.quantity < neededQuantity) {
                let element = document.getElementById(ingredientName + '-id') as HTMLElement;
                currentValues = currentValues.filter((item: string) => item !== ingredientName);
                this.form.patchValue({ "extraIngredients": currentValues });
                this.toggleElementSelected(element);
            }
        }
        return neededQuantity;
    }

    selectIngredient = (value: string) => {
        let element = document.getElementById(value + '-id') as HTMLElement;
        let ingredient = this.pizzaIngredients.find((ingredient: IngredientDto) => ingredient.value === value) || null;

        if (!!ingredient && ingredient.quantity < this.getNeededQuantity(value)) {
            this.toastr.warning('Ingredient not available', `${value} is not available`);
            return;
        }

        let currentValues = this.form.value["extraIngredients"];
        if (currentValues.includes(value))
            currentValues = currentValues.filter((item: string) => item !== value);
        else
            currentValues.push(value);

        this.form.patchValue({ "extraIngredients": currentValues });

        this.toggleElementSelected(element);
        /* RECALC PRICE */
        this.recalculatePrice();
    }

    toggleElementSelected = (element: HTMLElement) => {
        if (element.className.indexOf('select-option-selected') < 0)
            element.classList.add('select-option-selected');
        else
            element.classList.remove('select-option-selected');
    }

    getPizzaNameInfo(value: string): string {
        let pizza = this.pizzas.find((pizza: { value: string, name: string }) => pizza.value === value) || null;
        if (!!pizza)
            return pizza.name + ' (' + this.currencyPipe.transform(pizza.price, 'EUR') + ')';
        return '';
    }

    getPizzaSizeInfo(value: string): string {
        let size = this.pizzaSizes.find((size: { value: string, name: string }) => size.value === value) || null;
        if (!!size)
            return size.name + ' (' + this.currencyPipe.transform(size.price, 'EUR') + ')';
        return '';
    }

    getIngredientName(value: string): string {
        let ingredient = this.pizzaIngredients.find((ingredient: { value: string, name: string }) => ingredient.value === value) || null;
        if (!!ingredient)
            return ingredient.name + ' (' + this.currencyPipe.transform(ingredient.price, 'EUR') + ')';
        return '';
    }

    getPizzaDisplayInfo(value: string): string {
        console.log(value);
        let pizza = this.pizzas.find((pizza: { value: string, name: string }) => pizza.value === value) || null;
        if (!!pizza)
            return pizza.info;
        return '';
    }

    recalculatePrice = () => {
        let pizzaSize = this.form.value["pizzaSize"];
        let pizzaName = this.form.value["pizzaName"];
        let extraIngredients = this.form.value["extraIngredients"] as string[];
        let price = 0;

        if (!!pizzaSize)
            price += this.pizzaSizes.find((size: { value: string; }) => size.value === pizzaSize).price;

        if (!!pizzaName)
            price += this.pizzas.find((pizza: { value: string; }) => pizza.value === pizzaName).price;

        if (!!extraIngredients && extraIngredients.length > 0) {
            extraIngredients.forEach(ingredient => {
                price += this.pizzaIngredients.find((ing: { value: string; }) => ing.value === ingredient).price;
            });
        }

        this.form.patchValue({ "price": price });
    }

    /* 
      Post request of data
    */
    postRequestData = (data: PizzaDto) => {
        this.http.post<any>(`${environment.apiUrl}/order/placeorder`, data)
            .subscribe(
                data => {
                    this.toastr.success('Pizza ordered', "Pizza placed successfully!");
                    window.location.href = '/frontpage';
                },
                error => {
                    this.toastr.error('An error occurred', "Error occurred while placing the pizza, check console for more information");
                    console.error('Error:', error);
                    // Handle errors here
                }
            );
    }
}