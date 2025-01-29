import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Grid from "@mui/material/Grid2";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";

export default function Books() {
    const [books, setBooks] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        loadBooks();
    }, []);

    const loadBooks = async () => {
        try {
            const result = await axios.get("http://localhost:8081/books");
            const booksWithImages = await Promise.all(
                result.data.map(async (book) => {
                    try {
                        const imageResponse = await axios.get(
                            `http://localhost:8081/books/${book.id}/cover`
                        );
                        return { ...book, coverImage: `data:image/jpeg;base64,${imageResponse.data}` };
                    } catch (error) {
                        console.error(`Error loading cover image for book ID ${book.id}:`, error);
                        return { ...book, coverImage: null }; // Handle missing image
                    }
                })
            );
            setBooks(booksWithImages);
        } catch (error) {
            console.error("Error loading books:", error);
        }
    };

    return (
        <Grid container spacing={4} sx={{ padding: 4 }}>
            {books.length > 0 ? (
                books.map((book) => (
                    <Grid size={{xs:12, sm:6, md:4}} key={book.id}>
                        <Card sx={{ height: "100%", display: "flex", flexDirection: "column" }}>
                            <CardMedia
                                component="img"
                                alt={book.title}
                                sx={{
                                    height: '100%',
                                    objectFit: 'contain',
                                    width: '100%'
                                }}
                                image={book.coverImage || "/static/images/placeholder.jpg"} // Use placeholder if no image
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="div">
                                    {book.title}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    Author: {book.author}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    Quantity: {book.quantity}
                                </Typography>
                            </CardContent>
                            <Button
                                size="small"
                                variant="contained"
                                onClick={() => navigate(`/books/${book.id}`)}
                                sx={{ margin: 1 }}
                            >
                                Details
                            </Button>
                        </Card>
                    </Grid>
                ))
            ) : (
                <Typography variant="h6" align="center" sx={{ width: "100%", marginTop: 4 }}>
                    No books available
                </Typography>
            )}
        </Grid>
    );
}

