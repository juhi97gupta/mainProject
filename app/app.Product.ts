import { Merchant } from "./app.Merchant";

export class Product{
    productID: number;
    productName: string;
    merchant: Merchant;
    tag: string[];
    company: string;
    photo: string;
    description: string;
    quantity: number;
    category: string;
    subcategory: string;
    soldQuantities: number;
    price: number;
    releaseDate: Date;
}